package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.ErrorCode;
import com.example.community.common.SuccessCode;
import com.example.community.common.auth.AuthRequired;
import com.example.community.common.exception.ServiceException;
import com.example.community.docs.UserApiDoc;
import com.example.community.domain.User;
import com.example.community.dto.*;
import com.example.community.service.UserService;
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

    @UserApiDoc.SignUp
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

    @UserApiDoc.SignIn
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Map<String, String>>> signin(@Valid @RequestBody SignInRequest requestData) {
        String token = userService.signIn(requestData);
        Map<String, String> response = Map.of("token", token);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.SIGNIN_SUCCESS.getMessage(), response)
        );
    }

    @UserApiDoc.CheckDuplicated
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkDuplicated(
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
            message = isExisted ? ErrorCode.DUPLICATED_EMAIL.getMessage() : SuccessCode.EMAIL_AVAILABLE.getMessage();

            return ResponseEntity.ok(
                    ApiResponse.success(message, Map.of("available", !isExisted))
            );
        }

        // Nickname
        isExisted = userService.isExistedNickname(nickname);
        message = isExisted ? ErrorCode.DUPLICATED_NICKNAME.getMessage() : SuccessCode.NICKNAME_AVAILABLE.getMessage();

        return ResponseEntity.ok(
                ApiResponse.success(message, Map.of("available", !isExisted))
        );
    }

    @UserApiDoc.GetProfile
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

    @UserApiDoc.UpdateProfile
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

    @UserApiDoc.UpdatePassword
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

    @UserApiDoc.DeleteProfile
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
