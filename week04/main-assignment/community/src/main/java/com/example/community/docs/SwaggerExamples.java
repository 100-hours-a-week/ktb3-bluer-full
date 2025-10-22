package com.example.community.docs;

public final class SwaggerExamples {

    private SwaggerExamples() {
    }

    public static final String SIGNIN_REQUEST = """
            {
              "email": "user1@test.com",
              "password": "test"
            }
            """;

    public static final String SIGNIN_RESPONSE_SUCCESS = """
            {
              "message": "success",
              "data": {
                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
              }
            }
            """;

    public static final String SIGNIN_RESPONSE_FAILURE = """
            {
              "message": "login_failed",
              "data": null
            }
            """;

    public static final String SIGNUP_REQUEST = """
            {
              "email": "new-user@example.com",
              "password": "P@ssw0rd!",
              "nickname": "새유저",
              "profileImageUrl": "https://cdn.example.com/profile.jpg"
            }
            """;

    public static final String SIGNUP_RESPONSE_SUCCESS = """
            {
              "message": "register_success",
              "data": null
            }
            """;

    public static final String SIGNUP_RESPONSE_DUPLICATED = """
            {
              "message": "duplicated_email",
              "data": null
            }
            """;

    public static final String CHECK_DUPLICATED_RESPONSE_AVAILABLE = """
            {
              "message": "success",
              "data": {
                "available": true
              }
            }
            """;

    public static final String CHECK_DUPLICATED_RESPONSE_DUPLICATE = """
            {
              "message": "duplicated_nickname",
              "data": {
                "available": false
              }
            }
            """;

    public static final String CHECK_DUPLICATED_RESPONSE_INVALID = """
            {
              "message": "invalid_request",
              "data": null
            }
            """;

    public static final String PROFILE_RESPONSE_SUCCESS = """
            {
              "message": "success",
              "data": {
                "id": "user-123",
                "email": "user@example.com",
                "nickname": "닉네임",
                "profileImageUrl": "https://cdn.example.com/profile.jpg"
              }
            }
            """;

    public static final String PROFILE_RESPONSE_UNAUTHORIZED = """
            {
              "message": "unauthorized",
              "data": null
            }
            """;

    public static final String UPDATE_PROFILE_REQUEST = """
            {
              "nickname": "새로운닉네임",
              "profileImageUrl": "https://cdn.example.com/profile-new.jpg"
            }
            """;

    public static final String UPDATE_PROFILE_RESPONSE_SUCCESS = """
            {
              "message": "update_success",
              "data": {
                "id": "user-123",
                "email": "user@example.com",
                "nickname": "새로운닉네임",
                "profileImageUrl": "https://cdn.example.com/profile-new.jpg"
              }
            }
            """;

    public static final String UPDATE_PASSWORD_REQUEST = """
            {
              "password": "NewP@ssw0rd!"
            }
            """;

    public static final String UPDATE_PASSWORD_RESPONSE_SUCCESS = """
            {
              "message": "update_success",
              "data": null
            }
            """;

    public static final String DELETE_PROFILE_RESPONSE_SUCCESS = """
            {
              "message": "delete_success",
              "data": null
            }
            """;
}
