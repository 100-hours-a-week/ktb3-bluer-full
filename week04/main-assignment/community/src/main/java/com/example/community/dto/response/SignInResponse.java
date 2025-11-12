package com.example.community.dto.response;

public record SignInResponse(
        String userId,
        String token
) {
    public static SignInResponse of(String userId, String token) {
        return new SignInResponse(userId, token);
    }
}
