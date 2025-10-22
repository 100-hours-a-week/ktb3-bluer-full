package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.dto.SignInRequest;
import com.example.community.dto.SignUpRequest;
import com.example.community.dto.UpdatePasswordRequest;
import com.example.community.dto.UpdateProfileRequest;
import com.example.community.dto.UserProfileResponse;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Map<String, String> tokenStore = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        TODO: 유효성 검사

     */
    public User signup(SignUpRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
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

        String token = UUID.randomUUID().toString();

        tokenStore.put(token, user.getId());

        return token;
    }

    public Optional<User> findByToken(String token) {
        String userId = tokenStore.get(token);
        if (userId == null) {
            return Optional.empty();
        }

        return userRepository.findById(userId);
    }

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
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
        tokenStore.values().removeIf(id -> id.equals(user.getId()));
        userRepository.save(user);
    }
}
