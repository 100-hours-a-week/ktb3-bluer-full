package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.ErrorCode;
import com.example.community.common.SuccessCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.docs.UserApiDoc;
import com.example.community.domain.User;
import com.example.community.domain.validator.UserValidator;
import com.example.community.dto.request.CheckPasswordRequest;
import com.example.community.dto.request.SignInRequest;
import com.example.community.dto.request.SignUpRequest;
import com.example.community.dto.request.UpdatePasswordRequest;
import com.example.community.dto.request.UpdateProfileRequest;
import com.example.community.dto.response.SignInResponse;
import com.example.community.dto.response.UserProfileResponse;
import com.example.community.security.AuthCookieProvider;
import com.example.community.security.AuthenticatedUser;
import com.example.community.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final AuthCookieProvider authCookieProvider;

    public UserController(UserService userService, UserValidator userValidator, AuthCookieProvider authCookieProvider) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.authCookieProvider = authCookieProvider;
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
    public ResponseEntity<ApiResponse<SignInResponse>> signin(
            @Valid @RequestBody SignInRequest requestData,
            HttpServletResponse httpServletResponse
    ) {
        SignInResponse response = userService.signIn(requestData);
        httpServletResponse.addHeader("Set-Cookie", authCookieProvider.createTokenCookie(response.token()).toString());
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.SIGNIN_SUCCESS.getMessage(), response)
        );
    }

    @UserApiDoc.CheckPassword
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/password/check")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> checkPassword(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CheckPasswordRequest requestData
    ) {
        boolean matched = userService.isPasswordMatched(authenticatedUser.getUserId(), requestData.password());

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.REQUEST_SUCCESS.getMessage(),
                        Map.of("match", matched)
                )
        );
    }

    @UserApiDoc.SignOut
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Void>> signout(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            HttpServletResponse httpServletResponse
    ) {
        userService.signOut(authenticatedUser.getUserId());
        httpServletResponse.addHeader("Set-Cookie", authCookieProvider.createExpiredCookie().toString());

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.SIGNOUT_SUCCESS.getMessage())
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
            isExisted = userValidator.isExistedEmail(email);
            message = isExisted ? ErrorCode.DUPLICATED_EMAIL.getMessage() : SuccessCode.EMAIL_AVAILABLE.getMessage();

            return ResponseEntity.ok(
                    ApiResponse.success(message, Map.of("available", !isExisted))
            );
        }

        // Nickname
        isExisted = userValidator.isExistedNickname(nickname);
        message = isExisted ? ErrorCode.DUPLICATED_NICKNAME.getMessage() : SuccessCode.NICKNAME_AVAILABLE.getMessage();

        return ResponseEntity.ok(
                ApiResponse.success(message, Map.of("available", !isExisted))
        );
    }

    @UserApiDoc.GetProfile
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        User authUser = authenticatedUser.getUser();
        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.REQUEST_SUCCESS.getMessage(),
                        UserProfileResponse.from(authUser)
                )
        );
    }

    @UserApiDoc.UpdateProfile
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody UpdateProfileRequest requestData
    ) {
        User updatedUser = userService.updateProfile(authenticatedUser.getUserId(), requestData);

        return ResponseEntity.ok(
                ApiResponse.success(
                        SuccessCode.UPDATE_SUCCESS.getMessage(),
                        UserProfileResponse.from(updatedUser)
                )
        );
    }

    @UserApiDoc.UpdatePassword
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody UpdatePasswordRequest requestData
    ) {
        userService.updatePassword(authenticatedUser.getUserId(), requestData);

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.UPDATE_SUCCESS.getMessage())
        );
    }

    @UserApiDoc.DeleteProfile
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/profile")
    public ResponseEntity<ApiResponse<Void>> deleteProfile(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        userService.deleteProfile(authenticatedUser.getUserId());

        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.DELETE_SUCCESS.getMessage())
        );
    }
}
