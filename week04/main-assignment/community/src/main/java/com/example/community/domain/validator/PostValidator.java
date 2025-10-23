package com.example.community.domain.validator;

import com.example.community.common.ErrorCode;
import com.example.community.common.exception.ServiceException;
import com.example.community.domain.Post;
import com.example.community.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private final PostRepository postRepository;

    public PostValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post validateExists(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ServiceException(ErrorCode.POST_NOT_FOUND));
    }
}
