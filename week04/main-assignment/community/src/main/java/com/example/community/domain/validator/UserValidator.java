package com.example.community.domain.validator;

import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }
}
