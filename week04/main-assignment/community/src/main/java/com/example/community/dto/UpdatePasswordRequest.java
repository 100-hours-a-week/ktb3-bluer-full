package com.example.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;
}
