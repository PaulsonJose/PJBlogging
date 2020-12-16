package com.blogging.PJBlogging.mapper;

import com.blogging.PJBlogging.dto.VoteDto;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(target="post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteType", source = "voteDto.voteType")
    Vote mapToVote(VoteDto voteDto, Post post, User user);
}
