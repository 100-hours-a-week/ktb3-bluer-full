package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.ErrorCode;
import com.example.community.common.SuccessCode;
import com.example.community.common.auth.AuthRequired;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.dto.*;
import com.example.community.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignUpRequest requestData) {
        userService.signup(
                requestData
        );

        URI location = URI.create("/");

        return ResponseEntity.created(location).body(
                ApiResponse.success(SuccessCode.SIGNUP_SUCCESS.getMessage())
        );
    }

    @Operation(
            summary = "사용자 로그인",
            description = "이메일과 비밀번호를 검증하고 액세스 토큰을 발급합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignInRequest.class),
                            examples = @ExampleObject(
                                    name = "성공 요청",
                                    value = """
                                            {
                                              "email": "user1@test.com",
                                              "password": "test"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "성공 응답",
                                    value = """
                                            {
                                              "message": "success",
                                              "data": {
                                                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "이메일 또는 비밀번호가 올바르지 않음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "실패 응답",
                                    value = """
                                            {
                                              "message": "login_failed",
                                              "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Map<String, String>>> signin(@Valid @RequestBody SignInRequest requestData) {
        String token = userService.signIn(requestData);
        Map<String, String> response = Map.of("token", token);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.SIGNIN_SUCCESS.getMessage(), response)
        );
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkDuplicate(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname
    ) {
        if (email == null && nickname == null) {
            throw new ServiceException(ErrorCode.INVALID_REQUEST);
        }

        boolean isExisted;
        String message;

        // Email
        if (email != null) {
            isExisted = userService.isExistedEmail(email);
            message = isExisted ? ErrorCode.DUPLICATE_EMAIL.getMessage() : SuccessCode.EMAIL_AVAILABLE.getMessage();

            return ResponseEntity.ok(
                    ApiResponse.success(message, Map.of("available", !isExisted))
            );
        }

        // Nickname
        isExisted = userService.isExistedNickname(nickname);
        message = isExisted ? ErrorCode.DUPLICATE_NICKNAME.getMessage() : SuccessCode.NICKNAME_AVAILABLE.getMessage();

        return ResponseEntity.ok(
                ApiResponse.success(message, Map.of("available", !isExisted))
        );
    }

    @AuthRequired
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @RequestAttribute("authUser") User authUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.REQUEST_SUCCESS.getMessage(),
                        UserProfileResponse.from(authUser)
                )
        );
    }

    @AuthRequired
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @RequestAttribute("authUser") User authUser,
            @Valid @RequestBody UpdateProfileRequest requestData
    ) {
        User updatedUser = userService.updateProfile(authUser.getId(), requestData);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.UPDATE_SUCCESS.getMessage(),
                        UserProfileResponse.from(updatedUser)
                )
        );
    }

    @AuthRequired
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @RequestAttribute("authUser") User authUser,
            @Valid @RequestBody UpdatePasswordRequest requestData
    ) {
        userService.updatePassword(authUser.getId(), requestData);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.UPDATE_SUCCESS.getMessage())
        );
    }

    @AuthRequired
    @DeleteMapping("/profile")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(
            @RequestAttribute("authUser") User authUser
    ) {
        userService.deleteProfile(authUser.getId());

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.DELETE_SUCCESS.getMessage())
        );
    }
}
