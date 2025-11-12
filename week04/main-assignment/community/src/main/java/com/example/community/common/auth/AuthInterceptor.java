package com.example.community.common.auth;

import com.example.community.common.ApiResponse;
import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.User;
import com.example.community.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {


    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    public AuthInterceptor(TokenService tokenService, ObjectMapper objectMapper) {
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        AuthRequired authRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);
        String header = request.getHeader("Authorization");

        // NOTE: Swagger 포함 인증 필요 없는 요청은 통과
        if (authRequired == null) {
            attachUserIfPresent(header, request);
            return true;
        }

        if (header == null || !header.startsWith("Bearer ")) {
            writeErrorResponse(response, ErrorCode.UNAUTHORIZED);
            return false;
        }

        String token = header.replace("Bearer ", "").trim();
        User user = tokenService.findByToken(token)
                .orElseThrow(() -> new ServiceException(ErrorCode.USER_NOT_FOUND));

        request.setAttribute("authUser", user);
        return true;
    }

    private void attachUserIfPresent(String header, HttpServletRequest request) {
        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }
        String token = header.replace("Bearer ", "").trim();
        tokenService.findByToken(token)
                .ifPresent(user -> request.setAttribute("authUser", user));
    }

    private void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiResponse<Void> apiResponse = ApiResponse.error(errorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
