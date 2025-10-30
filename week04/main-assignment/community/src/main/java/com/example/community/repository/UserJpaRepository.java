package com.example.community.repository;

import com.example.community.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmailAndDeletedFalse(String email);

    Optional<UserEntity> findByNicknameAndDeletedFalse(String nickname);

    List<UserEntity> findByDeletedFalse();

    Optional<UserEntity> findByIdAndDeletedFalse(String id);
}
