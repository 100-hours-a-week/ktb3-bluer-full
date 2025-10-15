package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.dto.SignInRequest;
import com.example.community.dto.SignUpRequest;
import com.example.community.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return ResponseEntity.created(location).body(ApiResponse.success("회원가입 성공"));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Map<String, String>>> signin(@Valid @RequestBody SignInRequest requestData) {
        String token = userService.signIn(requestData);
        Map<String, String> response = Map.of("token", token);

        return ResponseEntity.ok(ApiResponse.success("로그인 성공", response));
    }
}
