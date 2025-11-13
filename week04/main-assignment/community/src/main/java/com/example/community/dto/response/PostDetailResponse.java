package com.example.community.dto.response;

import com.example.community.domain.Post;
import com.example.community.domain.User;

public record PostDetailResponse(
        String postId,
        String title,
        String authorId,
        String authorName,
        String authorProfileImageUrl,
        String content,
        String postImageUrl,
        int likeCount,
        int commentCount,
        int viewCount,
        String createdAt,
        boolean likedByMe
) {
    public static PostDetailResponse of(Post post, User author, boolean likedByMe) {
        return new PostDetailResponse(
                post.getPostId(),
                post.getTitle(),
                post.getAuthorId(),
                author.getNickname(),
                author.getProfileImageUrl(),
                post.getContent(),
                post.getPostImageUrl(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getViewCount(),
                post.getCreatedAt(),
                likedByMe
        );
    }
}
