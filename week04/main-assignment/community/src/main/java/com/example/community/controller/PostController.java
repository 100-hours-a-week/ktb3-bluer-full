package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.docs.PostApiDoc;
import com.example.community.dto.request.CreatePostRequest;
import com.example.community.dto.request.UpdatePostRequest;
import com.example.community.dto.response.PostDetailResponse;
import com.example.community.dto.response.PostListResponse;
import com.example.community.security.AuthenticatedUser;
import com.example.community.service.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @RequestParam(name = "size", defaultValue = "5") int size,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        String viewerId = authenticatedUser != null ? authenticatedUser.getUserId() : null;
        PostListResponse response = postService.getPosts(cursor, size, viewerId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", response));
    }

    @PostApiDoc.GetPost
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> getPost(
            @PathVariable String postId,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        String viewerId = authenticatedUser != null ? authenticatedUser.getUserId() : null;
        PostDetailResponse response = postService.getPostById(postId, viewerId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", response));
    }

    @PostApiDoc.LikePost
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Void>> likePost(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable String postId
    ) {
        postService.likePost(postId, authenticatedUser.getUserId());
        return ResponseEntity.ok(ApiResponse.success("like_success"));
    }

    @PostApiDoc.UnlikePost
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<Void>> unlikePost(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable String postId
    ) {
        postService.unlikePost(postId, authenticatedUser.getUserId());
        return ResponseEntity.ok(ApiResponse.success("unlike_success"));
    }

    @PostApiDoc.CreatePost
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestBody CreatePostRequest request
    ) {
        postService.createPost(authenticatedUser.getUserId(), request.title(), request.content(), request.imageUrl());
        return ResponseEntity.status(201).body(ApiResponse.success("create_success"));
    }

    @PostApiDoc.UpdatePost
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable String postId,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(postId, authenticatedUser.getUserId(), request.title(), request.content(), request.imageUrl());
        return ResponseEntity.ok(ApiResponse.success("update_success"));
    }

    @PostApiDoc.DeletePost
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable String postId
    ) {
        postService.deletePost(postId, authenticatedUser.getUserId());
        return ResponseEntity.ok(ApiResponse.success("delete_success"));
    }
}
