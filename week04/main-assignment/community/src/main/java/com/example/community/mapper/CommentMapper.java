package com.example.community.mapper;

import com.example.community.common.util.DateTimeUtils;
import com.example.community.domain.Comment;
import com.example.community.entity.CommentEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public CommentEntity mapToEntity(Comment comment, CommentEntity existing) {
        LocalDateTime now = DateTimeUtils.currentUtc();
        LocalDateTime createdAt = existing != null
                ? existing.getCreatedAt()
                : DateTimeUtils.parseToUtc(comment.getCreatedAt(), now);

        LocalDateTime updatedAtFallback = existing != null && existing.getUpdatedAt() != null
                ? existing.getUpdatedAt()
                : now;
        LocalDateTime updatedAt = DateTimeUtils.parseToUtc(comment.getUpdatedAt(), updatedAtFallback);

        return (existing != null ? existing.toBuilder() : CommentEntity.builder())
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public Comment mapToDomain(CommentEntity entity) {
        return Comment.builder()
                .commentId(entity.getCommentId())
                .postId(entity.getPostId())
                .authorId(entity.getAuthorId())
                .content(entity.getContent())
                .createdAt(DateTimeUtils.toIsoString(entity.getCreatedAt()))
                .updatedAt(DateTimeUtils.toIsoString(entity.getUpdatedAt()))
                .build();
    }
}
