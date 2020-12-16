package com.blogging.PJBlogging.mapper;

import com.blogging.PJBlogging.dto.CommentDto;
import com.blogging.PJBlogging.model.Comment;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment dtoToComment(CommentDto commentDto, Post post, User user);

    @Mapping(target = "userName", expression = "java(comment.getUser().getUserName())")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPost_Id())")
    CommentDto commentToDto(Comment comment);
}
