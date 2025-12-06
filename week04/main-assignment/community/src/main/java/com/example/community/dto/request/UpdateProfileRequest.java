package com.example.community.dto.request;

public record UpdateProfileRequest(
        String nickname,
        String profileImageUrl
) {
}
