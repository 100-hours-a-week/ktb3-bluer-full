package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Comment;
import com.example.community.domain.User;
import com.example.community.domain.validator.PostValidator;
import com.example.community.dto.mapper.CommentResponseMapper;
import com.example.community.dto.request.CreateCommentRequest;
import com.example.community.dto.request.UpdateCommentRequest;
import com.example.community.dto.response.CommentResponse;
import com.example.community.repository.CommentRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentResponseMapper commentResponseMapper;
    private final PostValidator postValidator;

    public CommentService(
            CommentRepository commentRepository,
            UserRepository userRepository,
            PostRepository postRepository,
            CommentResponseMapper commentResponseMapper,
            PostValidator postValidator
    ) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentResponseMapper = commentResponseMapper;
        this.postValidator = postValidator;
    }

    public List<CommentResponse> getComments(String postId) {
        postValidator.validateExists(postId);

        List<Comment> comments = commentRepository.findByPostId(postId);

        Map<String, User> userMap = userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return commentResponseMapper.toResponseList(comments, userMap);
    }

    public void createComment(String postId, String authorId, CreateCommentRequest request) {
        postValidator.validateExists(postId);

        String now = Instant.now().toString();
        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .postId(postId)
                .authorId(authorId)
                .content(request.content())
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

        comment.updateContent(request.content(), Instant.now().toString());
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
        int commentCount = commentRepository.countByPostId(postId);
        postRepository.findById(postId)
                .ifPresent(post -> {
                    post.updateCommentCount(commentCount);
                    postRepository.save(post);
                });
    }
}
