package com.example.community.service;

import com.example.community.domain.User;
import com.example.community.dto.SignUpRequest;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        TODO: 유효성 검사

     */
    public User signup(SignUpRequest request) {
//        if (userRepository.findByEmail(email).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
//        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .profileImageUrl(request.profileImageUrl())
                .build();

        return userRepository.save(user);
    }
}
