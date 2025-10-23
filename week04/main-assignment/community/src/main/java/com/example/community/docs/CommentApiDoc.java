package com.example.community.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class CommentApiDoc {

    private CommentApiDoc() {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "댓글 목록 조회",
            description = "게시글 ID에 해당하는 댓글 목록을 조회합니다."
    )
    @Parameter(name = "postId", description = "댓글을 조회할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 목록 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.COMMENT_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "존재하지 않음",
                                    value = SwaggerExamples.POST_NOT_FOUND_RESPONSE
                            )
                    )
            )
    })
    public @interface GetComments {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "댓글 작성",
            description = "로그인한 사용자가 게시글에 댓글을 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = SwaggerExamples.CREATE_COMMENT_REQUEST
                            )
                    )
            )
    )
    @Parameter(name = "postId", description = "댓글을 작성할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 작성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.CREATE_COMMENT_RESPONSE_SUCCESS
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 본문이 유효하지 않음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "잘못된 요청",
                                    value = SwaggerExamples.INVALID_REQUEST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = SwaggerExamples.UNAUTHORIZED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "게시글을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "존재하지 않음",
                                    value = SwaggerExamples.POST_NOT_FOUND_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateComment {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "댓글 수정",
            description = "작성자 본인만 댓글 내용을 수정할 수 있습니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = SwaggerExamples.UPDATE_COMMENT_REQUEST
                            )
                    )
            )
    )
    @Parameters({
            @Parameter(name = "postId", description = "댓글이 포함된 게시글 ID", required = true, example = "post-123"),
            @Parameter(name = "commentId", description = "수정할 댓글 ID", required = true, example = "comment-123")
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.UPDATE_COMMENT_RESPONSE_SUCCESS
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "작성자가 아닌 사용자의 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = SwaggerExamples.UNAUTHORIZED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "존재하지 않음",
                                    value = SwaggerExamples.COMMENT_NOT_FOUND_RESPONSE
                            )
                    )
            )
    })
    public @interface UpdateComment {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "댓글 삭제", description = "작성자 본인만 댓글을 삭제할 수 있습니다.")
    @Parameters({
            @Parameter(name = "postId", description = "댓글이 포함된 게시글 ID", required = true, example = "post-123"),
            @Parameter(name = "commentId", description = "삭제할 댓글 ID", required = true, example = "comment-123")
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.DELETE_COMMENT_RESPONSE_SUCCESS
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "작성자가 아닌 사용자의 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "인증 실패",
                                    value = SwaggerExamples.UNAUTHORIZED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "댓글을 찾을 수 없음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "존재하지 않음",
                                    value = SwaggerExamples.COMMENT_NOT_FOUND_RESPONSE
                            )
                    )
            )
    })
    public @interface DeleteComment {
    }
}
