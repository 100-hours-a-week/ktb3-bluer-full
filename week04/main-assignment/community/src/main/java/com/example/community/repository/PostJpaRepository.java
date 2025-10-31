package com.example.community.repository;

import com.example.community.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostJpaRepository extends JpaRepository<PostEntity, String> {

    List<PostEntity> findAllByOrderByCreatedAtDesc();
}
