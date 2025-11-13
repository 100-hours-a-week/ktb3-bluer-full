package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.dto.response.PostDetailResponse;
import com.example.community.dto.response.PostListResponse;
import com.example.community.dto.response.PostSummaryResponse;
import com.example.community.entity.PostLikeEntity;
import com.example.community.repository.PostLikeRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postLikeRepository = postLikeRepository;
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

    public PostDetailResponse getPostById(String postId, String viewerId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
        post.increaseViewCount();
        Post savedPost = postRepository.save(post);

        User author = userRepository.findById(savedPost.getAuthorId())
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        boolean likedByViewer = viewerId != null && postLikeRepository.existsByPostIdAndUserId(postId, viewerId);

        return PostDetailResponse.of(savedPost, author, likedByViewer);
    }

    public Post createPost(String authorId, String title, String content, String postImageUrl) {
        Post post = Post.builder()
                .postId(UUID.randomUUID().toString())
                .title(title)
                .authorId(authorId)
                .content(content)
                .postImageUrl(postImageUrl)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .createdAt(Instant.now().toString())
                .build();

        return postRepository.save(post);
    }

    public Post updatePost(String postId, String authorId, String title, String content, String postImageUrl) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        if (!post.getAuthorId().equals(authorId)) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        post.update(title, content, postImageUrl);
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

    @Transactional
    public void likePost(String postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        boolean alreadyLiked = postLikeRepository.existsByPostIdAndUserId(postId, userId);
        if (alreadyLiked) {
            throw new ServiceException(ErrorCode.POST_ALREADY_LIKED);
        }

        post.increaseLikeCount();
        postRepository.save(post);
        postLikeRepository.save(postId, userId);
    }

    @Transactional
    public void unlikePost(String postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));

        PostLikeEntity likeEntity = postLikeRepository.findActiveByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_LIKED));

        post.decreaseLikeCount();
        postRepository.save(post);
        postLikeRepository.deactivate(likeEntity);
    }
}
