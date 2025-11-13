package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.auth.AuthRequired;
import com.example.community.docs.PostApiDoc;
import com.example.community.domain.User;
import com.example.community.dto.request.CreatePostRequest;
import com.example.community.dto.request.UpdatePostRequest;
import com.example.community.dto.response.PostDetailResponse;
import com.example.community.dto.response.PostListResponse;
import com.example.community.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostApiDoc.GetPosts
    @GetMapping
    public ResponseEntity<ApiResponse<PostListResponse>> getPosts(
            @RequestParam(name = "cursor", defaultValue = "0") int cursor,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        PostListResponse response = postService.getPosts(cursor, size);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", response));
    }

    @PostApiDoc.GetPost
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @PathVariable String postId,
            @RequestAttribute(value = "authUser", required = false) User authUser
    ) {
        String viewerId = authUser != null ? authUser.getId() : null;
        PostDetailResponse response = postService.getPostById(postId, viewerId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", response));
    }

    @AuthRequired
    @PostApiDoc.LikePost
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Void>> likePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId
    ) {
        postService.likePost(postId, authUser.getId());
        return ResponseEntity.ok(ApiResponse.success("like_success"));
    }

    @AuthRequired
    @PostApiDoc.UnlikePost
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Void>> unlikePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId
    ) {
        postService.unlikePost(postId, authUser.getId());
        return ResponseEntity.ok(ApiResponse.success("unlike_success"));
    }

    @AuthRequired
    @PostApiDoc.CreatePost
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestAttribute("authUser") User authUser,
            @RequestBody CreatePostRequest request
    ) {
        postService.createPost(authUser.getId(), request.title(), request.content(), request.imageUrl());
        return ResponseEntity.status(201).body(ApiResponse.success("create_success"));
    }

    @AuthRequired
    @PostApiDoc.UpdatePost
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(postId, authUser.getId(), request.title(), request.content(), request.imageUrl());
        return ResponseEntity.ok(ApiResponse.success("update_success"));
    }

    @AuthRequired
    @PostApiDoc.DeletePost
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId
    ) {
        postService.deletePost(postId, authUser.getId());
        return ResponseEntity.ok(ApiResponse.success("delete_success"));
    }
}
