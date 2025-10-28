package com.example.community.service;

import com.example.community.domain.User;
import com.example.community.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final UserRepository userRepository;

    private final ConcurrentHashMap<String, String> tokenStore = new ConcurrentHashMap<>();

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String issueToken(User user) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return token;
    }

    public void removeToken(String userId) {
        tokenStore.values().removeIf(id -> id.equals(userId));
    }

    public Optional<User> findByToken(String token) {
        String userId = tokenStore.get(token);
        if (userId == null) {
            return Optional.empty();
        }
        return userRepository.findById(userId);
    }

}
