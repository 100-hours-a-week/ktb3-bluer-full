package com.example.community.repository;

import com.example.community.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private static final String FILE_PATH = "data/users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<User> readUsers() {
        try {
            File file = new File(FILE_PATH);

            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("사용자 데이터 읽기 실패", e);
        }
    }

    private void writeUsers(List<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            throw new RuntimeException("사용자 데이터 저장 실패", e);
        }
    }

    public User save(User user) {
        List<User> users = readUsers();

        users = users.stream()
                .filter(u -> !u.getId().equals(user.getId()))
                .collect(Collectors.toList());
        users.add(user);
        writeUsers(users);

        return user;
    }

    public Optional<User> findById(String id) {
        return readUsers().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return readUsers().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<User> findByNickname(String nickname) {
        return readUsers().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getNickname().equals(nickname))
                .findFirst();
    }

    public List<User> findAll() {
        return readUsers().stream()
                .filter(u -> !u.isDeleted())
                .collect(Collectors.toList());
    }
}
