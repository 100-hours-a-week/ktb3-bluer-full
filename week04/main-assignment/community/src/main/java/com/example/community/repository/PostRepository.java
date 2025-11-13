package com.example.community.repository;

import com.example.community.domain.Post;
import com.example.community.entity.PostEntity;
import com.example.community.mapper.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final PostMapper postMapper;

    public PostRepository(PostJpaRepository postJpaRepository, PostMapper postMapper) {
        this.postJpaRepository = postJpaRepository;
        this.postMapper = postMapper;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postJpaRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(postMapper::mapToDomain);
    }

    public List<Post> findByAuthorId(String authorId) {
        return postJpaRepository.findByAuthorId(authorId).stream()
                .map(postMapper::mapToDomain)
                .toList();
    }

    public Optional<Post> findById(String postId) {
        return postJpaRepository.findById(postId)
                .map(postMapper::mapToDomain);
    }

    public Post save(Post post) {
        PostEntity entity = postJpaRepository.findById(post.getPostId())
                .map(existing -> postMapper.mapToEntity(post, existing))
                .orElseGet(() -> postMapper.mapToEntity(post, null));

        PostEntity saved = postJpaRepository.save(entity);
        return postMapper.mapToDomain(saved);
    }

    public void delete(String postId) {
        postJpaRepository.deleteById(postId);
    }
}
