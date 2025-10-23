package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 수정 요청 본문")
public record UpdatePostRequest(
        @Schema(description = "변경할 게시글 제목", example = "수정된 게시글 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String title,

        @Schema(description = "변경할 게시글 내용", example = "수정된 게시글 본문 내용입니다.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String content
) {
}
