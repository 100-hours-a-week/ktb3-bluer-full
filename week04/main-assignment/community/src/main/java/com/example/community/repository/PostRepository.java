package com.example.community.repository;

import com.example.community.domain.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostRepository extends JsonFileRepository<Post> {
    private static final String FILE_PATH = "data/posts.json";

    public PostRepository() {
        super(
                FILE_PATH,
                new ObjectMapper(),
                new TypeReference<List<Post>>() {
                },
                "게시글 데이터 읽기 실패",
                "게시글 데이터 저장 실패"
        );
    }

    public List<Post> findAll() {
        return readAll().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Post> findById(String postId) {
        return readAll().stream()
                .filter(p -> p.getPostId().equals(postId))
                .findFirst();
    }

    public Post save(Post post) {
        List<Post> posts = readAll();
        posts = posts.stream()
                .filter(p -> !p.getPostId().equals(post.getPostId()))
                .collect(Collectors.toList());
        posts.add(post);
        writeAll(posts);
        return post;
    }

    public void delete(String postId) {
        List<Post> posts = readAll();
        posts.removeIf(p -> p.getPostId().equals(postId));
        writeAll(posts);
    }
}
