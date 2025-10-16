package com.example.community.dto;

import com.example.community.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private final String commentId;
    private final String content;
    private final CommentAuthorResponse author;
    private final String createdAt;
    private final String updatedAt;

    public static CommentResponse of(Comment comment, CommentAuthorResponse author) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                author,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
