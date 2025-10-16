package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Comment;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.dto.CommentAuthorResponse;
import com.example.community.dto.CommentResponse;
import com.example.community.dto.CreateCommentRequest;
import com.example.community.dto.UpdateCommentRequest;
import com.example.community.repository.CommentRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<CommentResponse> getComments(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        return commentRepository.findByPostId(post.getPostId()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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

    private CommentResponse toResponse(Comment comment) {
        User author = userRepository.findById(comment.getAuthorId())
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        CommentAuthorResponse authorResponse = new CommentAuthorResponse(
                author.getId(),
                author.getNickname(),
                author.getProfileImageUrl()
        );

        return CommentResponse.of(comment, authorResponse);
    }

    private void updatePostCommentCount(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
        int commentCount = commentRepository.countByPostId(postId);
        post.updateCommentCount(commentCount);
        postRepository.save(post);
    }
}
