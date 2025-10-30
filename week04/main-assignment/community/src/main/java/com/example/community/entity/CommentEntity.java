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
@Table(name = "Comment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentEntity {

    @Id
    @Column(name = "댓글 ID", length = 50, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "댓글 작성자 ID", nullable = false)
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "댓글이 작성된 포스트 ID", nullable = false)
    private PostEntity post;

    @Column(name = "댓글 내용", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "댓글 생성일시", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "댓글 수정일시")
    private LocalDateTime updatedAt;
}
