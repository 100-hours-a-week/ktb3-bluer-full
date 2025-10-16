package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.auth.AuthRequired;
import com.example.community.domain.Post;
import com.example.community.domain.User;
import com.example.community.dto.CreatePostRequest;
import com.example.community.dto.UpdatePostRequest;
import com.example.community.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<Post>> getPost(@PathVariable String postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", post));
    }

    @AuthRequired
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPost(
            @RequestAttribute("authUser") User authUser,
            @RequestBody CreatePostRequest request
    ) {
        postService.createPost(authUser.getId(), request.getTitle(), request.getContent());
        return ResponseEntity.status(201).body(ApiResponse.success("create_success"));
    }

    @AuthRequired
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId,
            @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(postId, authUser.getId(), request.getTitle(), request.getContent());
        return ResponseEntity.ok(ApiResponse.success("update_success"));
    }

    @AuthRequired
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestAttribute("authUser") User authUser,
            @PathVariable String postId
    ) {
        postService.deletePost(postId, authUser.getId());
        return ResponseEntity.ok(ApiResponse.success("delete_success"));
    }
}
