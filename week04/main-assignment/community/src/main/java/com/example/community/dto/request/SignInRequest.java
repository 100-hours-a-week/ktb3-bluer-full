package com.example.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SignInRequest", description = "로그인 요청에 필요한 데이터 형식")
public record SignInRequest(
        @Schema(description = "사용자 이메일 주소", example = "user1@test.com")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Schema(description = "로그인 비밀번호", example = "test")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
