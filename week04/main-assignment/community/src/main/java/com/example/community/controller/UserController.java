package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.domain.User;
import com.example.community.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody User requestData) {
        userService.signup(
                requestData.getEmail(),
                requestData.getPassword(),
                requestData.getNickname(),
                requestData.getProfileImageUrl()
        );

        return ResponseEntity.ok(ApiResponse.success("회원가입 성공"));
    }
}
