package com.example.community.repository;

import com.example.community.domain.User;
import com.example.community.entity.UserEntity;
import com.example.community.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public User save(User user) {
        UserEntity entity = userJpaRepository.findById(user.getId())
                .map(existing -> userMapper.mapToEntity(user, existing))
                .orElseGet(() -> userMapper.mapToEntity(user, null));

        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.mapToDomain(saved);
    }

    public Optional<User> findById(String id) {
        return userJpaRepository.findByIdAndDeletedFalse(id)
                .map(userMapper::mapToDomain);
    }

    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeletedFalse(email)
                .map(userMapper::mapToDomain);
    }

    public Optional<User> findByNickname(String nickname) {
        return userJpaRepository.findByNicknameAndDeletedFalse(nickname)
                .map(userMapper::mapToDomain);
    }

    public List<User> findAll() {
        return userJpaRepository.findByDeletedFalse().stream()
                .map(userMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    public List<User> findAllByIds(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return userJpaRepository.findByIdInAndDeletedFalse(ids).stream()
                .map(userMapper::mapToDomain)
                .collect(Collectors.toList());
    }
}
