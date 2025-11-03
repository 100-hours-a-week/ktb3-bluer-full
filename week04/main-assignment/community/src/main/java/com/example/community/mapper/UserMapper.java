package com.example.community.mapper;

import com.example.community.common.util.DateTimeUtils;
import com.example.community.domain.User;
import com.example.community.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserEntity mapToEntity(User user, UserEntity existing) {
        LocalDateTime now = DateTimeUtils.currentUtc();
        LocalDateTime createdAt = existing != null
                ? existing.getCreatedAt()
                : (user.getCreatedAt() != null ? user.getCreatedAt() : now);

        return (existing != null ? existing.toBuilder() : UserEntity.builder())
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .deleted(user.isDeleted())
                .createdAt(createdAt)
                .updatedAt(now)
                .build();
    }

    public User mapToDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .profileImageUrl(entity.getProfileImageUrl())
                .deleted(Boolean.TRUE.equals(entity.getDeleted()))
                .createdAt(entity.getCreatedAt())
                .build();

    }
}
