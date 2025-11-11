package com.example.community.dto.response;

import java.util.List;

public record PostListResponse(
        List<PostSummaryResponse> posts,
        Integer nextCursor,
        boolean hasNext
) {
}
