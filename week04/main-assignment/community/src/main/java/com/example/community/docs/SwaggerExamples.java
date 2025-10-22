package com.example.community.docs;

public final class SwaggerExamples {

    private SwaggerExamples() {
    }

    public static final String SIGNIN_REQUEST = """
            {
              "email": "user@example.com",
              "password": "P@ssw0rd!"
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
}
