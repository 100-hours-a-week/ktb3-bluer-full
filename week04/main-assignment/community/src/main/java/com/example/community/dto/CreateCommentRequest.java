package com.example.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 작성 요청 본문")
public record CreateCommentRequest(
        @Schema(description = "작성할 댓글 내용", example = "좋은 글 잘 읽었습니다!")
        @NotBlank(message = "댓글은 공백일 수 없습니다.")
        String content
) {
}
