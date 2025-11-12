package com.example.community.repository;

import com.example.community.common.util.DateTimeUtils;
import com.example.community.entity.PostLikeEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostLikeRepository {

    private final PostLikeJpaRepository postLikeJpaRepository;

    public PostLikeRepository(PostLikeJpaRepository postLikeJpaRepository) {
        this.postLikeJpaRepository = postLikeJpaRepository;
    }

    public boolean existsByPostIdAndUserId(String postId, String userId) {
        return postLikeJpaRepository.existsByPostIdAndUserIdAndActiveTrue(postId, userId);
    }

    public Optional<PostLikeEntity> findActiveByPostIdAndUserId(String postId, String userId) {
        return postLikeJpaRepository.findByPostIdAndUserIdAndActiveTrue(postId, userId);
    }

    public void save(String postId, String userId) {
        LocalDateTime now = DateTimeUtils.currentUtc();
        PostLikeEntity entity = PostLikeEntity.builder()
                .likeId(UUID.randomUUID().toString())
                .postId(postId)
                .userId(userId)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        postLikeJpaRepository.save(entity);
    }

    public void deactivate(PostLikeEntity likeEntity) {
        PostLikeEntity updated = likeEntity.toBuilder()
                .active(false)
                .updatedAt(DateTimeUtils.currentUtc())
                .build();
        postLikeJpaRepository.save(updated);
    }
}
