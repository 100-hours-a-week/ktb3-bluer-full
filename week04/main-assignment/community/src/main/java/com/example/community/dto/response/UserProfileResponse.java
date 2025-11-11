package com.example.community.dto.response;

import com.example.community.domain.User;

public record UserProfileResponse(
        String id,
        String email,
        String nickname,
        String profileImageUrl
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl()
        );
    }
}
