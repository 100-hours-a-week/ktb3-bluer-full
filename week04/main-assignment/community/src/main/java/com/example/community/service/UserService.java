package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.domain.validator.UserValidator;
import com.example.community.dto.SignInRequest;
import com.example.community.dto.SignUpRequest;
import com.example.community.dto.UpdatePasswordRequest;
import com.example.community.dto.UpdateProfileRequest;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, UserValidator userValidator, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.tokenService = tokenService;
    }

    public void signup(SignUpRequest request) {
        if (userValidator.isExistedEmail(request.email())) {
            throw new ServiceException(ErrorCode.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .profileImageUrl(request.profileImageUrl())
                .build();

        userRepository.save(user);
    }

    public String signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ServiceException(ErrorCode.LOGIN_FAILED));

        if (!user.getPassword().equals(request.password())) {
            throw new ServiceException(ErrorCode.LOGIN_FAILED);
        }

        return tokenService.issueToken(user);
    }

    @Transactional
    public User updateProfile(String userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.updateProfile(request.getNickname(), request.getProfileImageUrl());
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(String userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.updatePassword(request.getPassword());
        userRepository.save(user);
    }

    @Transactional
    public void deleteProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        user.markAsDeleted();
        tokenService.removeToken(user.getId());
        userRepository.save(user);
    }
}
