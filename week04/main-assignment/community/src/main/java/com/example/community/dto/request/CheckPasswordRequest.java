package com.example.community.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckPasswordRequest(
        @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
        String password
) {
}
