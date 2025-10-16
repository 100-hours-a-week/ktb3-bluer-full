package com.example.community.dto;

import com.example.community.domain.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private final List<Post> posts;
    private final Integer nextCursor;
    private final boolean hasNext;
}
