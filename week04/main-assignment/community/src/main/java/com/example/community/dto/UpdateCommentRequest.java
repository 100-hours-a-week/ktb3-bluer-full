package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 수정 요청 본문")
public record UpdateCommentRequest(
        @Schema(description = "수정할 댓글 내용", example = "내용을 조금 더 다듬었습니다.")
        @NotBlank
        String content
) {
}
