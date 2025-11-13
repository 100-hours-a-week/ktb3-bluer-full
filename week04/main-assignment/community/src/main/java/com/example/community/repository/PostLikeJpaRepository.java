package com.example.community.repository;

import com.example.community.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PostLikeJpaRepository extends JpaRepository<PostLikeEntity, String> {
    boolean existsByPostIdAndUserIdAndActiveTrue(String postId, String userId);
    Optional<PostLikeEntity> findByPostIdAndUserIdAndActiveTrue(String postId, String userId);

    @Transactional
    @Modifying
    void deleteByPostId(String postId);
}
