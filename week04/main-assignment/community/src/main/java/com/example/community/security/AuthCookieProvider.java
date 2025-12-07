package com.example.community.security;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AuthCookieProvider {

    public static final String AUTH_COOKIE_NAME = "AUTH_TOKEN";
    private static final Duration COOKIE_MAX_AGE = Duration.ofDays(7);

    public ResponseCookie createTokenCookie(String token) {
        return ResponseCookie.from(AUTH_COOKIE_NAME, token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(COOKIE_MAX_AGE)
                .build();
    }

    public ResponseCookie createExpiredCookie() {
        return ResponseCookie.from(AUTH_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(0)
                .build();
    }
}
