package com.example.community.repository;

import com.example.community.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<String, User> store = new HashMap<>();

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Optional<User> findByNickname(String nickname) {
        return store.values().stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getNickname().equals(nickname))
                .findFirst();
    }
}
