package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.common.util.DateTimeUtils;
import com.example.community.domain.Comment;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.domain.validator.UserValidator;
import com.example.community.dto.request.SignInRequest;
import com.example.community.dto.request.SignUpRequest;
import com.example.community.dto.request.UpdatePasswordRequest;
import com.example.community.dto.request.UpdateProfileRequest;
import com.example.community.dto.response.SignInResponse;
import com.example.community.repository.CommentRepository;
import com.example.community.repository.PostLikeRepository;
import com.example.community.repository.PostRepository;
import com.example.community.repository.UserRepository;
import com.example.community.security.AuthenticatedUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final TokenService tokenService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserValidator userValidator,
            TokenService tokenService,
            PostRepository postRepository,
            CommentRepository commentRepository,
            PostLikeRepository postLikeRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.tokenService = tokenService;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignUpRequest request) {
        if (userValidator.isExistedEmail(request.email())) {
            throw new ServiceException(ErrorCode.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .profileImageUrl(request.profileImageUrl())
                .createdAt(DateTimeUtils.currentUtc())
                .build();

        userRepository.save(user);
    }

    public SignInResponse signIn(SignInRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
            User user = principal.getUser();
            String token = tokenService.issueToken(user);
            return SignInResponse.of(user.getId(), token);
        } catch (AuthenticationException e) {
            throw new ServiceException(ErrorCode.LOGIN_FAILED);
        }
    }

    public boolean isPasswordMatched(String userId, String rawPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void signOut(String userId) {
        tokenService.removeToken(userId);
    }

    @Transactional
    public User updateProfile(String userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        boolean hasNickname = request.nickname() != null && !request.nickname().isBlank();
        boolean hasProfileImageUrl = request.profileImageUrl() != null && !request.profileImageUrl().isBlank();

        if (!hasNickname && !hasProfileImageUrl) {
            throw new ServiceException(ErrorCode.INVALID_REQUEST);
        }

        user.updateProfile(request.nickname(), request.profileImageUrl());
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(String userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.updatePassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        Set<String> deletedPostIds = deleteUserPosts(userId);
        deleteUserComments(userId, deletedPostIds);
        user.markAsDeleted();
        tokenService.removeToken(user.getId());
        userRepository.save(user);
    }

    private Set<String> deleteUserPosts(String userId) {
        List<Post> posts = postRepository.findByAuthorId(userId);
        Set<String> deletedPostIds = new HashSet<>();

        for (Post post : posts) {
            String postId = post.getPostId();
            commentRepository.deleteByPostId(postId);
            postLikeRepository.deleteByPostId(postId);
            postRepository.delete(postId);
            deletedPostIds.add(postId);
        }
        return deletedPostIds;
    }

    private void deleteUserComments(String userId, Set<String> excludedPostIds) {
        List<Comment> comments = commentRepository.findByAuthorId(userId).stream()
                .filter(comment -> !excludedPostIds.contains(comment.getPostId()))
                .toList();

        Set<String> affectedPostIds = new HashSet<>();
        for (Comment comment : comments) {
            commentRepository.delete(comment.getPostId(), comment.getCommentId());
            affectedPostIds.add(comment.getPostId());
        }

        for (String postId : affectedPostIds) {
            int commentCount = commentRepository.countByPostId(postId);
            postRepository.findById(postId).ifPresent(post -> {
                post.updateCommentCount(commentCount);
                postRepository.save(post);
            });
        }
    }
}
