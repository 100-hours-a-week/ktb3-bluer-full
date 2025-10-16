package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.auth.AuthRequired;
import com.example.community.domain.User;
import com.example.community.dto.CommentResponse;
import com.example.community.dto.CreateCommentRequest;
import com.example.community.dto.UpdateCommentRequest;
import com.example.community.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/post/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable String postId
    ) {
        List<CommentResponse> comments = commentService.getComments(postId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", comments));
    }

    @AuthRequired
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createComment(
            @PathVariable String postId,
            @RequestAttribute("authUser") User authUser,
            @Valid @RequestBody CreateCommentRequest request
    ) {
        commentService.createComment(postId, authUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("create_success"));
    }

    @AuthRequired
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestAttribute("authUser") User authUser,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        commentService.updateComment(postId, commentId, authUser.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("update_success"));
    }

    @AuthRequired
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestAttribute("authUser") User authUser
    ) {
        commentService.deleteComment(postId, commentId, authUser.getId());
        return ResponseEntity.ok(ApiResponse.success("delete_success"));
    }
}
