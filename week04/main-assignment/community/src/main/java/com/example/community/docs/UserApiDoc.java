package com.example.community.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class UserApiDoc {

    private UserApiDoc() {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "사용자 회원가입",
            description = "이메일, 비밀번호, 닉네임, 프로필 이미지를 등록합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = """
                                            {
                                              "email": "user@example.com",
                                              "password": "P@ssw0rd!",
                                              "nickname": "닉네임",
                                              "profileImageUrl": "https://cdn.example.com/profile.jpg"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 요청 데이터",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    public @interface SignUp {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "사용자 로그인",
            description = "이메일과 비밀번호를 검증하고 액세스 토큰을 발급합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "성공 요청", value = SwaggerExamples.SIGNIN_REQUEST)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "성공 응답", value = SwaggerExamples.SIGNIN_RESPONSE_SUCCESS)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "이메일 또는 비밀번호가 올바르지 않음",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "실패 응답", value = SwaggerExamples.SIGNIN_RESPONSE_FAILURE)
                    )
            )
    })
    public @interface SignIn {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "이메일/닉네임 중복 검사",
            description = "이메일 또는 닉네임이 이미 존재하는지 확인합니다. (query params: email, nickname)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "중복 여부 반환",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    public @interface CheckDuplicate {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "사용자 프로필 조회", description = "로그인된 사용자의 프로필 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 조회 성공",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "권한이 없습니다.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(name = "실패 응답", value = SwaggerExamples.SIGNIN_RESPONSE_FAILURE)
                    )
            )})
    public @interface GetProfile {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "프로필 수정", description = "닉네임, 프로필 이미지를 수정합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "프로필 수정 성공",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    public @interface UpdateProfile {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호를 확인하고 새 비밀번호로 변경합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "비밀번호 변경 성공",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    public @interface UpdatePassword {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(summary = "회원 탈퇴", description = "사용자 계정을 삭제합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "회원 탈퇴 성공",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    )
    public @interface DeleteProfile {
    }
}
