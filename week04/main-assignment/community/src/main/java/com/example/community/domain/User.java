package com.example.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String nickname;
    private String profileImageUrl;
    private boolean deleted;

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        if (deleted) {
            throw new IllegalStateException("삭제된 계정입니다.");
        }
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (profileImageUrl != null && !profileImageUrl.isBlank()) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public void updatePassword(String password) {
        if (deleted) {
            throw new IllegalStateException("삭제된 계정입니다.");
        }
        this.password = password;
    }
}
