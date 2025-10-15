package com.example.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateProfileRequest {

    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    private String nickname;

    private String profileImageUrl;
}
