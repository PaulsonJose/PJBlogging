package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.dto.CommentDto;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.mapper.CommentMapper;
import com.blogging.PJBlogging.model.NotificationEmail;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.repository.CommentRepository;
import com.blogging.PJBlogging.repository.PostRepository;
import com.blogging.PJBlogging.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private static final String POST_URL = "";

    @Transactional
    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(()-> new SpringPJBloggingException("Post cannot be found."));
        commentRepository.save(commentMapper.dtoToComment(commentDto,post,authService.getCurrentUser()));
        String message = mailContentBuilder.build
            (authService.getCurrentUser().getUserName() + " Posted a comment on your post." + POST_URL);
        sendNotificationMessage(message, post.getUser());
    }

    private void sendNotificationMessage(String message, User user) {
        mailService.sendEmail(new NotificationEmail(
                authService.getCurrentUser().getUserName() + "has commented on your post.",
                user.getEmail(),message));
    }

    public List<CommentDto> getCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new SpringPJBloggingException("Post not found"));
        return commentRepository.findByPost(post).stream()
                .map(commentMapper::commentToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getCommentsForUser(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(()->new SpringPJBloggingException("User not found."));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::commentToDto).collect(Collectors.toList());
    }
}
