package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Post;
import com.example.community.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        return postRepository.save(post);
    }

    public Post createPost(String authorId, String title, String content) {
        Post post = Post.builder()
                .postId(UUID.randomUUID().toString())
                .title(title)
                .authorId(authorId)
                .content(content)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .createdAt(Instant.now().toString())
                .build();

        return postRepository.save(post);
    }

    public Post updatePost(String postId, String authorId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        if (!post.getAuthorId().equals(authorId)) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        post.update(title, content);
        return postRepository.save(post);
    }

    public void deletePost(String postId, String authorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        if (!post.getAuthorId().equals(authorId)) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        postRepository.delete(postId);
    }
}
