package com.example.community.service;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.dto.SignInRequest;
import com.example.community.dto.SignUpRequest;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
            throw new ServiceException(ErrorCode.DUPLICATE_EMAIL);
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

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
