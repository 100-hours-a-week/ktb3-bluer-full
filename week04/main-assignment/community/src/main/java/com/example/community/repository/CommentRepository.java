package com.example.community.repository;

import com.example.community.domain.Comment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {

    private static final String FILE_PATH = "data/comments.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Comment> findByPostId(String postId) {
        return readAll().stream()
                .filter(postComments -> postComments.postId.equals(postId))
                .findFirst()
                .map(postComments -> postComments.comments.stream()
                        .map(commentData -> toDomain(postId, commentData))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Optional<Comment> findByIds(String postId, String commentId) {
        return readAll().stream()
                .filter(postComments -> postComments.postId.equals(postId))
                .findFirst()
                .flatMap(postComments -> postComments.comments.stream()
                        .filter(commentData -> commentData.commentId.equals(commentId))
                        .findFirst()
                        .map(commentData -> toDomain(postId, commentData)));
    }

    public Comment save(Comment comment) {
        List<PostCommentsData> all = readAll();
        PostCommentsData postComments = all.stream()
                .filter(data -> data.postId.equals(comment.getPostId()))
                .findFirst()
                .orElseGet(() -> {
                    PostCommentsData data = new PostCommentsData();
                    data.postId = comment.getPostId();
                    all.add(data);
                    return data;
                });

        postComments.comments.removeIf(existing -> existing.commentId.equals(comment.getCommentId()));
        postComments.comments.add(toData(comment));
        writeAll(all);
        return comment;
    }

    public void delete(String postId, String commentId) {
        List<PostCommentsData> all = readAll();
        Iterator<PostCommentsData> iterator = all.iterator();
        while (iterator.hasNext()) {
            PostCommentsData data = iterator.next();
            if (!data.postId.equals(postId)) {
                continue;
            }
            data.comments.removeIf(comment -> comment.commentId.equals(commentId));
            if (data.comments.isEmpty()) {
                iterator.remove();
            }
            break;
        }
        writeAll(all);
    }

    public int countByPostId(String postId) {
        return findByPostId(postId).size();
    }

    private Comment toDomain(String postId, CommentData commentData) {
        return Comment.builder()
                .commentId(commentData.commentId)
                .postId(postId)
                .authorId(commentData.authorId)
                .content(commentData.content)
                .createdAt(commentData.createdAt)
                .updatedAt(commentData.updatedAt)
                .build();
    }

    private CommentData toData(Comment comment) {
        CommentData data = new CommentData();
        data.commentId = comment.getCommentId();
        data.authorId = comment.getAuthorId();
        data.content = comment.getContent();
        data.createdAt = comment.getCreatedAt();
        data.updatedAt = comment.getUpdatedAt();
        return data;
    }

    private List<PostCommentsData> readAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<PostCommentsData>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("댓글 데이터 읽기 실패", e);
        }
    }

    private void writeAll(List<PostCommentsData> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), data);
        } catch (IOException e) {
            throw new RuntimeException("댓글 데이터 저장 실패", e);
        }
    }

    private static class PostCommentsData {
        public String postId;
        public List<CommentData> comments = new ArrayList<>();
    }

    private static class CommentData {
        public String commentId;
        public String authorId;
        public String content;
        public String createdAt;
        public String updatedAt;
    }
}
