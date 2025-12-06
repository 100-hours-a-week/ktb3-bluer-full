package com.example.community.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SIGNUP_SUCCESS("register_success"),
    SIGNIN_SUCCESS("success"),
    SIGNOUT_SUCCESS("logout_success"),

    EMAIL_AVAILABLE("success"),
    NICKNAME_AVAILABLE("available"),

    REQUEST_SUCCESS("success"),
    UPDATE_SUCCESS("update_success"),
    DELETE_SUCCESS("delete_success");

    private final String message;
}
