package com.example.community.dto;

import com.example.community.domain.Comment;

public record CommentResponse(
        String commentId,
        String content,
        CommentAuthorResponse author,
        String createdAt,
        String updatedAt
) {
    public static CommentResponse of(Comment comment, CommentAuthorResponse author) {
        String updatedAt = comment.getUpdatedAt() != null ? comment.getUpdatedAt() : comment.getCreatedAt();
        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                author,
                comment.getCreatedAt(),
                updatedAt
        );
    }
}
