package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.dto.PostRequest;
import com.blogging.PJBlogging.dto.PostResponse;
import com.blogging.PJBlogging.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor

public class PostController {

    private final PostService postService;
    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest){
        PostResponse postResponse = postService.create(postRequest); 
        return status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostById(id));
    }
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }
    @GetMapping("by-subblog/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubblog(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostBySubblogId(id));
    }
    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostByUser(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostByUserName(name));
    }

}
