package com.blogging.PJBlogging.mapper;

import com.blogging.PJBlogging.dto.SubblogDto;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.Subblog;
import com.blogging.PJBlogging.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubblogMapper {

    SubblogMapper INSTANCE = Mappers.getMapper(SubblogMapper.class);

    @Mapping(target = "postCount", expression = "java(mapPosts(subblog.getPosts()))")
    SubblogDto mapSubblogDto(Subblog subblog);

    default Integer mapPosts(List<Post> postList) {
        return postList.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    Subblog mapSubblog (SubblogDto subblogDto, User user);
}
