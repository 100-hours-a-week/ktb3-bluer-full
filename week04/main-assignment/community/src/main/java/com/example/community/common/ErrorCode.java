package com.example.community.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "duplicate_email"),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "duplicate_nickname"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "invalid_request"),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "login_failed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user_not_found"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "invalid_token"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post_not_found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "comment_not_found");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
