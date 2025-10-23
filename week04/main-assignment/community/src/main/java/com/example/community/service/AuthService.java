package com.example.community.service;

import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
