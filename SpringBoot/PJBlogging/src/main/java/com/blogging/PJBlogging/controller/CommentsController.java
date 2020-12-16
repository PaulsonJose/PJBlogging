package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.dto.CommentDto;
import com.blogging.PJBlogging.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController

@RequestMapping("/api/comments")
@AllArgsConstructor

public class CommentsController {

    private final CommentService commentService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable("postId") Long postId) {
        return status(HttpStatus.OK).body(commentService.getCommentsForPost(postId));
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<CommentDto>> getCommentByUser(@PathVariable String userName){
        return status(HttpStatus.OK).body(commentService.getCommentsForUser(userName));
    }
}
