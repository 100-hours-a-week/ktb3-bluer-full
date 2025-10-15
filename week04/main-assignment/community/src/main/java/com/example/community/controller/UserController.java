package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.ErrorCode;
import com.example.community.common.SuccessCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.dto.SignInRequest;
import com.example.community.dto.SignUpRequest;
import com.example.community.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@Controller
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

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getMyInfo(
            @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.replace("Bearer ", "").trim();

        User user = userService.findByToken(token)
                .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_REQUEST));

        return ResponseEntity.ok(ApiResponse.success("내 정보 조회 성공", user));
    }

}
