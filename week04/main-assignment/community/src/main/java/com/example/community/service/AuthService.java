package com.example.community.service;

import com.example.community.domain.User;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public String issueToken(User user) {
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

    public void invalidateUserTokens(String userId) {
        tokenStore.values().removeIf(id -> id.equals(userId));
    }
}
