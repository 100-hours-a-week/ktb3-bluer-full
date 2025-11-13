package com.example.community.repository;

import com.example.community.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, String> {

    List<CommentEntity> findByPostIdOrderByCreatedAtAsc(String postId);

    Optional<CommentEntity> findByCommentIdAndPostId(String commentId, String postId);

    long countByPostId(String postId);

    List<CommentEntity> findByAuthorId(String authorId);

    @Transactional
    @Modifying
    void deleteByPostId(String postId);
}
