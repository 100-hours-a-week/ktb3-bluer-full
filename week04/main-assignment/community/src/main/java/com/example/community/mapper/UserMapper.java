package com.example.community.mapper;

import com.example.community.domain.User;
import com.example.community.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public UserEntity updateExisting(UserEntity existing, User user) {
        return existing.toBuilder()
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .deleted(user.isDeleted())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserEntity createNew(User user) {
        LocalDateTime now = LocalDateTime.now();
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .deleted(user.isDeleted())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .profileImageUrl(entity.getProfileImageUrl())
                .deleted(Boolean.TRUE.equals(entity.getDeleted()))
                .build();

    }
}
