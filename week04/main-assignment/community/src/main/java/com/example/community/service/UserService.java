package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.dto.*;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public User signup(SignUpRequest request) {
        if (authService.isExistedEmail(request.email())) {
            throw new ServiceException(ErrorCode.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .profileImageUrl(request.profileImageUrl())
                .build();

        return userRepository.save(user);
    }

    public String signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ServiceException(ErrorCode.LOGIN_FAILED));

        if (!user.getPassword().equals(request.password())) {
            throw new ServiceException(ErrorCode.LOGIN_FAILED);
        }

        return authService.issueToken(user);
    }

    @Transactional
    public User updateProfile(String userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.updateProfile(request.getNickname(), request.getProfileImageUrl());
        return userRepository.save(user);
    }

    @Transactional
    public UserProfileResponse updatePassword(String userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.updatePassword(request.getPassword());
        userRepository.save(user);
        return UserProfileResponse.from(user);
    }

    @Transactional
    public void deleteProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.markAsDeleted();
        authService.invalidateUserTokens(user.getId());
        userRepository.save(user);
    }
}
