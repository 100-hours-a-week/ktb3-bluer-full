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

    public void updateProfile(String nickname, String profileImageUrl) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (profileImageUrl != null && !profileImageUrl.isBlank()) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
