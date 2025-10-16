package com.example.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    private String postId;
    private String title;
    private String authorId;
    private String content;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private String createdAt;

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }
}
