package com.example.community.dto;

import com.example.community.domain.Post;

import java.util.List;

public record PostListResponse(
        List<Post> posts,
        Integer nextCursor,
        boolean hasNext
) {
}
