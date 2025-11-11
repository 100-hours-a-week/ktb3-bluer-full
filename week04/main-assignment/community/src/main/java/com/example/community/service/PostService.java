package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.dto.response.PostDetailResponse;
import com.example.community.dto.response.PostListResponse;
import com.example.community.dto.response.PostSummaryResponse;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostListResponse getPosts(int cursor, int size) {
        if (cursor < 0 || size <= 0) {
            throw new ServiceException(ErrorCode.INVALID_REQUEST);
        }

        PageRequest pageable = PageRequest.of(cursor, size);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> posts = page.getContent();

        Set<String> authorIds = posts.stream()
                .map(Post::getAuthorId)
                .collect(Collectors.toSet());

        Map<String, User> userMap = userRepository.findAllByIds(authorIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<PostSummaryResponse> postResponses = posts.stream()
                .map(post -> {
                    User author = userMap.get(post.getAuthorId());
                    if (author == null) {
                        throw new ServiceException(ErrorCode.USER_NOT_FOUND);
                    }
                    return PostSummaryResponse.of(post, author);
                })
                .toList();

        boolean hasNext = page.hasNext();
        Integer nextCursor = hasNext ? page.getNumber() + 1 : null;

        return new PostListResponse(postResponses, nextCursor, hasNext);
    }

    public PostDetailResponse getPostById(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        Post savedPost = postRepository.save(post);

        User author = userRepository.findById(savedPost.getAuthorId())
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        return PostDetailResponse.of(savedPost, author);
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
