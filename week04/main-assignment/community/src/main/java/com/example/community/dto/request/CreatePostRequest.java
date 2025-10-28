package com.example.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시글 생성 요청 본문")
public record CreatePostRequest(
        @Schema(description = "게시글 제목", example = "첫 번째 게시글")
        @NotBlank
        String title,

        @Schema(description = "게시글 내용", example = "게시글 본문 내용입니다.")
        @NotBlank
        String content
) {
}
