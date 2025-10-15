package com.example.community.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SIGNUP_SUCCESS("회원가입 성공"),
    SIGNIN_SUCCESS("로그인 성공"),

    EMAIL_AVAILABLE("이메일 사용 가능"),
    NICKNAME_AVAILABLE("닉네임 사용 가능"),

    REQUEST_SUCCESS("요청 성공"),
    UPDATE_SUCCESS("수정 성공"),
    DELETE_SUCCESS("삭제 성공");

    private final String message;
}
