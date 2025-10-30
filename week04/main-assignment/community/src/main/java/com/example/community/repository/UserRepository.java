package com.example.community.repository;

import com.example.community.domain.User;
import com.example.community.entity.UserEntity;
import com.example.community.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepository(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    public User save(User user) {
        UserEntity entity = userJpaRepository.findById(user.getId())
                .map(existing -> userMapper.updateExisting(existing, user))
                .orElseGet(() -> userMapper.createNew(user));

        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }

    public Optional<User> findById(String id) {
        return userJpaRepository.findByIdAndDeletedFalse(id)
                .map(userMapper::toDomain);
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedFalse(email)
                .map(userMapper::toDomain);
    }

    public Optional<User> findByNickname(String nickname) {
        return userJpaRepository.findByNicknameAndDeletedFalse(nickname)
                .map(userMapper::toDomain);
    }

    public List<User> findAll() {
        return userJpaRepository.findByDeletedFalse().stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }
}
