package com.example.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentAuthorResponse {
    private final String id;
    private final String nickname;
    private final String profileImageUrl;
}
