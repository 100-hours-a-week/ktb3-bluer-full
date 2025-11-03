package com.example.community.repository;

import com.example.community.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<PostEntity, String> {

    Page<PostEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
