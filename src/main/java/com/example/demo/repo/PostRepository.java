package com.example.demo.repo;

import com.example.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long> {
    List <Post> findByName(String name);

}
