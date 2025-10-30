package com.example.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "User")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "유저ID", length = 50, nullable = false)
    private String id;

    @Column(name = "이메일", length = 100, nullable = false)
    private String email;

    @Column(name = "비밀번호", length = 255, nullable = false)
    private String password;

    @Column(name = "닉네임", length = 50, nullable = false)
    private String nickname;

    @Column(name = "프로필 이미지 URL", length = 255)
    private String profileImageUrl;

    @Column(name = "회원 생성일시")
    private LocalDateTime createdAt;

    @Column(name = "회원 정보 업데이트 일시")
    private LocalDateTime updatedAt;

    @Column(name = "삭제 여부")
    private Boolean deleted;

    @Builder.Default
    @OneToMany(mappedBy = "author")
    private List<PostEntity> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "author")
    private List<CommentEntity> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<PostLikeEntity> postLikes = new ArrayList<>();
}
