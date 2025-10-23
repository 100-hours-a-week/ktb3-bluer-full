package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Comment;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.dto.CommentResponse;
import com.example.community.dto.CreateCommentRequest;
import com.example.community.dto.UpdateCommentRequest;
import com.example.community.dto.mapper.CommentResponseMapper;
import com.example.community.repository.CommentRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Function;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentResponseMapper commentResponseMapper;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository,
            CommentResponseMapper commentResponseMapper
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentResponseMapper = commentResponseMapper;
    }

    public List<CommentResponse> getComments(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPostId(post.getPostId());

        Map<String, User> userMap = userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return commentResponseMapper.toResponseList(comments, userMap);
    }

    public void createComment(String postId, String authorId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        String now = Instant.now().toString();
        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .postId(postId)
                .authorId(authorId)
                .content(request.getContent())
                .createdAt(now)
                .updatedAt(now)
                .build();

        commentRepository.save(comment);
        updatePostCommentCount(postId);
    }

    public void updateComment(String postId, String commentId, String authorId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findByIds(postId, commentId)
                .orElseThrow(() -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthorId().equals(authorId)) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        comment.updateContent(request.getContent(), Instant.now().toString());
        commentRepository.save(comment);
    }

    public void deleteComment(String postId, String commentId, String authorId) {
        Comment comment = commentRepository.findByIds(postId, commentId)
                .orElseThrow(() -> new ServiceException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getAuthorId().equals(authorId)) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        commentRepository.delete(postId, commentId);
        updatePostCommentCount(postId);
    }

    private void updatePostCommentCount(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
        int commentCount = commentRepository.countByPostId(postId);
        post.updateCommentCount(commentCount);
        postRepository.save(post);
    }
}
