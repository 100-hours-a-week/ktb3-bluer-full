package com.example.community.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글은 공백일 수 없습니다.")
    private String content;
}
