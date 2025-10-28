package com.example.community.repository;

import com.example.community.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository extends JsonFileRepository<User> {
    private static final String FILE_PATH = "data/users.json";

    public UserRepository() {
        super(
                FILE_PATH,
                new ObjectMapper(),
                new TypeReference<List<User>>() {
                },
                "사용자 데이터 읽기 실패",
                "사용자 데이터 저장 실패"
        );
    }

    public User save(User user) {
        List<User> users = readAll();

        users = users.stream()
                .filter(u -> !u.getId().equals(user.getId()))
                .collect(Collectors.toList());
        users.add(user);
        writeAll(users);

        return user;
    }

    public Optional<User> findById(String id) {
        return readAll().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return readAll().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    public Optional<User> findByNickname(String nickname) {
        return readAll().stream()
                .filter(u -> !u.isDeleted())
                .filter(u -> u.getNickname().equals(nickname))
                .findFirst();
    }

    public List<User> findAll() {
        return readAll().stream()
                .filter(u -> !u.isDeleted())
                .collect(Collectors.toList());
    }
}
