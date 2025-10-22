package com.example.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private String commentId;
    private String postId;
    private String authorId;
    private String content;
    private String createdAt;
    private String updatedAt;

    public void updateContent(String content, String updatedAt) {
        this.content = content;
        this.updatedAt = updatedAt;
    }
}
