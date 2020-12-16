package com.blogging.PJBlogging.mapper;

import com.blogging.PJBlogging.dto.PostRequest;
import com.blogging.PJBlogging.dto.PostResponse;
import com.blogging.PJBlogging.model.*;
import com.blogging.PJBlogging.repository.CommentRepository;
import com.blogging.PJBlogging.repository.VoteRepository;
import com.blogging.PJBlogging.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.blogging.PJBlogging.model.VoteType.DOWNVOTE;
import static com.blogging.PJBlogging.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private VoteRepository voteRepository;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subblog", source = "subblog")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subblog subblog, User user);

    @Mapping(target = "id", source = "post_Id")
    @Mapping(target = "postName", source = "post_Name")
    @Mapping(target = "subblogName", source = "subblog.name")
    @Mapping(target = "userName", source = "user.userName")
    @Mapping(target="commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote",expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote",expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto (Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    boolean checkVoteType(Post post, VoteType voteType) {
        if(authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }
}
