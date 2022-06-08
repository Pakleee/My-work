package com.example.demo.repo;

import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
        List<Post> findByNameStartingWithIgnoreCase(String name);
        List<Post> findByNameEndingWithIgnoreCase(String name);
        List<Post> findByNameContainingIgnoreCase(String name);
    }

