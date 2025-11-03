package com.example.community.repository;

import com.example.community.domain.Comment;
import com.example.community.entity.CommentEntity;
import com.example.community.mapper.CommentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentMapper commentMapper;

    public CommentRepository(CommentJpaRepository commentJpaRepository, CommentMapper commentMapper) {
        this.commentJpaRepository = commentJpaRepository;
        this.commentMapper = commentMapper;
    }

    public List<Comment> findByPostId(String postId) {
        return commentJpaRepository.findByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(commentMapper::mapToDomain)
                .toList();
    }

    public Optional<Comment> findByIds(String postId, String commentId) {
        return commentJpaRepository.findByCommentIdAndPostId(commentId, postId)
                .map(commentMapper::mapToDomain);
    }

    public Comment save(Comment comment) {
        CommentEntity entity = commentJpaRepository.findById(comment.getCommentId())
                .map(existing -> commentMapper.mapToEntity(comment, existing))
                .orElseGet(() -> commentMapper.mapToEntity(comment, null));

        CommentEntity saved = commentJpaRepository.save(entity);
        return commentMapper.mapToDomain(saved);
    }

    public void delete(String postId, String commentId) {
        commentJpaRepository.findByCommentIdAndPostId(commentId, postId)
                .ifPresent(commentJpaRepository::delete);
    }

    public int countByPostId(String postId) {
        return Math.toIntExact(commentJpaRepository.countByPostId(postId));
    }
}

