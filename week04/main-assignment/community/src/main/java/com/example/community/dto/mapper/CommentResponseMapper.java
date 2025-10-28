package com.example.community.dto.mapper;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Comment;
import com.example.community.domain.User;
import com.example.community.dto.response.CommentAuthorResponse;
import com.example.community.dto.response.CommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommentResponseMapper {

    public List<CommentResponse> toResponseList(List<Comment> comments, Map<String, User> userMap) {
        return comments.stream()
                .map(comment -> toResponse(comment, userMap))
                .collect(Collectors.toList());
    }

    private CommentResponse toResponse(Comment comment, Map<String, User> userMap) {
        User author = userMap.get(comment.getAuthorId());
        if (author == null) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }

        CommentAuthorResponse authorResponse = new CommentAuthorResponse(
                author.getId(),
                author.getNickname(),
                author.getProfileImageUrl()
        );

        return CommentResponse.of(comment, authorResponse);
    }
}
