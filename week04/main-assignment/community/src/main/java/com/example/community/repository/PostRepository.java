package com.example.community.repository;

import com.example.community.domain.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private static final String FILE_PATH = "data/posts.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Post> readPosts() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return objectMapper.readValue(file, new TypeReference<List<Post>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("게시글 데이터 읽기 실패", e);
        }
    }

    private void writePosts(List<Post> posts) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), posts);
        } catch (IOException e) {
            throw new RuntimeException("게시글 데이터 저장 실패", e);
        }
    }

    public List<Post> findAll() {
        return readPosts().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Post> findById(String postId) {
        return readPosts().stream()
                .filter(p -> p.getPostId().equals(postId))
                .findFirst();
    }

    public Post save(Post post) {
        List<Post> posts = readPosts();
        posts = posts.stream()
                .filter(p -> !p.getPostId().equals(post.getPostId()))
                .collect(Collectors.toList());
        posts.add(post);
        writePosts(posts);
        return post;
    }

    public void delete(String postId) {
        List<Post> posts = readPosts();
        posts.removeIf(p -> p.getPostId().equals(postId));
        writePosts(posts);
    }
}
