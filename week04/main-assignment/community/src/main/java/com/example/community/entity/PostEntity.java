package com.example.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Post")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostEntity {

    @Id
    @Column(name = "포스트 ID", length = 50, nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "포스트 작성자 유저 ID", nullable = false)
    private UserEntity author;

    @Column(name = "포스트 제목", length = 255, nullable = false)
    private String title;

    @Column(name = "포스트 본문", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "포스트 좋아요 수", nullable = false)
    private Integer likeCount;

    @Column(name = "포스트 댓글 수", nullable = false)
    private Integer commentCount;

    @Column(name = "포스트 조회수", nullable = false)
    private Integer viewCount;

    @Column(name = "포스트 생성일시", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "포스트 수정 일시")
    private LocalDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<PostLikeEntity> postLikes = new ArrayList<>();
}
