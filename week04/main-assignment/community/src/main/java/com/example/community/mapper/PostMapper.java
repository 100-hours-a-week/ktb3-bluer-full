package com.example.community.mapper;

import com.example.community.common.util.DateTimeUtils;
import com.example.community.domain.Post;
import com.example.community.entity.PostEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    public PostEntity mapToEntity(Post post, PostEntity existing) {
        LocalDateTime now = DateTimeUtils.currentUtc();
        LocalDateTime createdAt = existing != null
                ? existing.getCreatedAt()
                : DateTimeUtils.parseToUtc(post.getCreatedAt(), now);

        return (existing != null ? existing.toBuilder() : PostEntity.builder())
                .postId(post.getPostId())
                .authorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .createdAt(createdAt)
                .updatedAt(now)
                .build();
    }

    public Post mapToDomain(PostEntity entity) {
        return Post.builder()
                .postId(entity.getPostId())
                .authorId(entity.getAuthorId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .likeCount(valueOrZero(entity.getLikeCount()))
                .commentCount(valueOrZero(entity.getCommentCount()))
                .viewCount(valueOrZero(entity.getViewCount()))
                .createdAt(DateTimeUtils.toIsoString(entity.getCreatedAt()))
                .build();
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }
}
