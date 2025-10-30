package com.example.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PostLike")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostLikeEntity {

    @Id
    @Column(name = "좋아요 ID", length = 50, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "좋아요한 포스트 ID", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "좋아요 한 유저ID", nullable = false)
    private UserEntity user;

    @Column(name = "좋아요 여부")
    private Boolean liked;

    @Column(name = "좋아요 생성일시", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "좋아요 업데이트 일시")
    private LocalDateTime updatedAt;
}
