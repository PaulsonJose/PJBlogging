package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.dto.PostRequest;
import com.blogging.PJBlogging.dto.PostResponse;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.mapper.PostMapper;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.Subblog;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.repository.PostRepository;
import com.blogging.PJBlogging.repository.SubblogRepository;
import com.blogging.PJBlogging.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional

public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubblogRepository subblogRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public PostResponse create(PostRequest postRequest) {
        Subblog subblog = subblogRepository.findById(postRequest.getSubblogId())
                .orElseThrow(()->new SpringPJBloggingException("Blog not found: " + postRequest.getSubblogId()));
        Post post= postRepository.save(postMapper.map(postRequest,subblog,authService.getCurrentUser()));
        return postMapper.mapToDto(postRepository.findById(post.getPost_Id()).get());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubblogId(Long id) {
        Subblog subblog = subblogRepository.findById(id).
                orElseThrow(()->new SpringPJBloggingException("Blog Id not found" + id));
        List<Post> posts = postRepository.findAllBySubblog(subblog);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new SpringPJBloggingException("Post with Id: " + id + " not found."));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()->new SpringPJBloggingException("User "+ userName + "cannot be found."));
        List<Post> posts = postRepository.findByUser(user)
                .orElseThrow(()->new SpringPJBloggingException("No Posts found for user: " + user.getUserName()));
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
