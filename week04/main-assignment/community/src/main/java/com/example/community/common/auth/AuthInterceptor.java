package com.example.community.common.auth;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String header = request.getHeader("Authorization");
        System.out.println("preHandle");
        System.out.println("Authorization: " + header);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        String token = header.replace("Bearer ", "").trim();
        User user = userService.findByToken(token)
                .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_TOKEN));

        request.setAttribute("authUser", user);
        return true;
    }
}
