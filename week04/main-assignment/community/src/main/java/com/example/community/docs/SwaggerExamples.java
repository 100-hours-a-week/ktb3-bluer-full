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
                "userId": "user-123",
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

    public static final String CHECK_DUPLICATED_RESPONSE_DUPLICATED = """
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

    public static final String UNAUTHORIZED_RESPONSE = """
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

    public static final String POST_LIST_RESPONSE = """
            {
              "message": "fetch_success",
              "data": {
                "posts": [
                  {
                    "postId": "post-123",
                    "title": "첫 번째 게시글",
                    "authorId": "user-123",
                    "authorName": "홍길동",
                    "authorProfileImageUrl": "https://cdn.example.com/profile.jpg",
                    "content": "게시글 내용입니다.",
                    "postImageUrl": "https://cdn.example.com/post-image.jpg",
                    "likeCount": 12,
                    "commentCount": 3,
                    "viewCount": 57,
                    "createdAt": "2025-02-18T12:34:56Z"
                  }
                ],
                "nextCursor": 5,
                "hasNext": true
              }
            }
            """;

    public static final String INVALID_REQUEST_RESPONSE = """
            {
              "message": "invalid_request",
              "data": null
            }
            """;

    public static final String POST_DETAIL_RESPONSE = """
            {
              "message": "fetch_success",
              "data": {
                "postId": "post-123",
                "title": "첫 번째 게시글",
                "authorId": "user-123",
                "authorName": "홍길동",
                "authorProfileImageUrl": "https://cdn.example.com/profile.jpg",
                "content": "게시글 내용입니다.",
                "postImageUrl": "https://cdn.example.com/post-image.jpg",
                "likeCount": 12,
                "commentCount": 3,
                "viewCount": 58,
                "createdAt": "2025-02-18T12:35:10Z",
                "likedByMe": true
              }
            }
            """;

    public static final String POST_NOT_FOUND_RESPONSE = """
            {
              "message": "post_not_found",
              "data": null
            }
            """;

    public static final String POST_ALREADY_LIKED_RESPONSE = """
            {
              "message": "post_already_liked",
              "data": null
            }
            """;

    public static final String POST_NOT_LIKED_RESPONSE = """
            {
              "message": "post_not_liked",
              "data": null
            }
            """;

    public static final String POST_UNLIKE_RESPONSE_SUCCESS = """
            {
              "message": "unlike_success",
              "data": null
            }
            """;

    public static final String CREATE_POST_REQUEST = """
            {
              "title": "새로운 게시글 제목",
              "content": "게시글 본문 내용입니다.",
              "imageUrl": "https://cdn.example.com/post-image.jpg"
            }
            """;

    public static final String CREATE_POST_RESPONSE_SUCCESS = """
            {
              "message": "create_success",
              "data": null
            }
            """;

    public static final String UPDATE_POST_REQUEST = """
            {
              "title": "수정된 게시글 제목",
              "content": "수정된 게시글 본문 내용입니다.",
              "imageUrl": "https://cdn.example.com/post-image-new.jpg"
            }
            """;

    public static final String UPDATE_POST_RESPONSE_SUCCESS = """
            {
              "message": "update_success",
              "data": null
            }
            """;

    public static final String DELETE_POST_RESPONSE_SUCCESS = """
            {
              "message": "delete_success",
              "data": null
            }
            """;

    public static final String POST_LIKE_RESPONSE_SUCCESS = """
            {
              "message": "like_success",
              "data": null
            }
            """;

    public static final String COMMENT_LIST_RESPONSE = """
            {
              "message": "fetch_success",
              "data": [
                {
                  "commentId": "comment-123",
                  "content": "좋은 글 잘 읽었습니다!",
                  "author": {
                    "id": "user-456",
                    "nickname": "댓글러",
                    "profileImageUrl": "https://cdn.example.com/profile-commenter.jpg"
                  },
                  "createdAt": "2025-02-18T12:40:00Z",
                  "updatedAt": "2025-02-18T12:40:00Z"
                }
              ]
            }
            """;

    public static final String CREATE_COMMENT_REQUEST = """
            {
              "content": "좋은 글 잘 읽었습니다!"
            }
            """;

    public static final String CREATE_COMMENT_RESPONSE_SUCCESS = """
            {
              "message": "create_success",
              "data": null
            }
            """;

    public static final String UPDATE_COMMENT_REQUEST = """
            {
              "content": "내용을 조금 더 다듬었습니다."
            }
            """;

    public static final String UPDATE_COMMENT_RESPONSE_SUCCESS = """
            {
              "message": "update_success",
              "data": null
            }
            """;

    public static final String DELETE_COMMENT_RESPONSE_SUCCESS = """
            {
              "message": "delete_success",
              "data": null
            }
            """;

    public static final String COMMENT_NOT_FOUND_RESPONSE = """
            {
              "message": "comment_not_found",
              "data": null
            }
            """;
}
