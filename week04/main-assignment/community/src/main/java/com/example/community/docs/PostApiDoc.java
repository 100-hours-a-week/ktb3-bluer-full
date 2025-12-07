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

public final class PostApiDoc {

    private PostApiDoc() {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "게시글 목록 조회",
            description = "cursor 기반 페이지네이션으로 게시글 목록을 조회합니다."
    )
    @Parameters({
            @Parameter(name = "cursor", description = "다음 페이지 조회를 위한 시작 위치 (0 이상)", example = "0"),
            @Parameter(name = "size", description = "조회할 게시글 수 (1 이상)", example = "5")
    })
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 목록 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.POST_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "cursor 또는 size 값이 올바르지 않음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "잘못된 요청",
                                    value = SwaggerExamples.INVALID_REQUEST_RESPONSE
                            )
                    )
            )
    })
    public @interface GetPosts {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글 ID로 단일 게시글을 조회합니다."
    )
    @Parameter(name = "postId", description = "조회할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.POST_DETAIL_RESPONSE
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
    public @interface GetPost {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "게시글 작성",
            description = "로그인한 사용자가 새 게시글을 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = SwaggerExamples.CREATE_POST_REQUEST
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "게시글 작성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.CREATE_POST_RESPONSE_SUCCESS
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
            )
    })
    public @interface CreatePost {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "게시글 수정",
            description = "작성자 본인만 게시글 제목 및 내용을 수정할 수 있습니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = SwaggerExamples.UPDATE_POST_REQUEST
                            )
                    )
            )
    )
    @Parameter(name = "postId", description = "수정할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 수정 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.UPDATE_POST_RESPONSE_SUCCESS
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
    public @interface UpdatePost {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "게시글 삭제", description = "작성자 본인만 게시글을 삭제할 수 있습니다.")
    @Parameter(name = "postId", description = "삭제할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.DELETE_POST_RESPONSE_SUCCESS
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
    public @interface DeletePost {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "게시글 좋아요", description = "로그인한 사용자가 게시글에 좋아요를 남깁니다. 중복 좋아요는 허용되지 않습니다.")
    @Parameter(name = "postId", description = "좋아요할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 좋아요 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.POST_LIKE_RESPONSE_SUCCESS
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 좋아요를 누른 게시글",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "중복 요청",
                                    value = SwaggerExamples.POST_ALREADY_LIKED_RESPONSE
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
    public @interface LikePost {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "게시글 좋아요 취소", description = "사용자가 이전에 눌렀던 게시글 좋아요를 취소합니다.")
    @Parameter(name = "postId", description = "좋아요 취소할 게시글 ID", required = true, example = "post-123")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 좋아요 취소 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = SwaggerExamples.POST_UNLIKE_RESPONSE_SUCCESS
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "좋아요하지 않은 게시글",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "잘못된 요청",
                                    value = SwaggerExamples.POST_NOT_LIKED_RESPONSE
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
    public @interface UnlikePost {
    }
}
