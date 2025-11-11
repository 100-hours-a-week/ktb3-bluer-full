package com.example.community.controller;

import com.example.community.common.ApiResponse;
import com.example.community.common.auth.AuthRequired;
import com.example.community.docs.CommentApiDoc;
import com.example.community.domain.User;
import com.example.community.dto.request.CreateCommentRequest;
import com.example.community.dto.request.UpdateCommentRequest;
import com.example.community.dto.response.CommentResponse;
import com.example.community.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @CommentApiDoc.GetComments
    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(
            @PathVariable String postId
    ) {
        List<CommentResponse> comments = commentService.getComments(postId);
        return ResponseEntity.ok(ApiResponse.success("fetch_success", comments));
    }

    @AuthRequired
    @CommentApiDoc.CreateComment
    @SecurityRequirement(name = "bearerAuth")
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
    @CommentApiDoc.UpdateComment
    @SecurityRequirement(name = "bearerAuth")
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
    @CommentApiDoc.DeleteComment
    @SecurityRequirement(name = "bearerAuth")
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
