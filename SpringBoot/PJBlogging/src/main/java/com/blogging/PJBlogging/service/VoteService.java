package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.dto.VoteDto;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.mapper.VoteMapper;
import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.model.Vote;
import com.blogging.PJBlogging.repository.PostRepository;
import com.blogging.PJBlogging.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.blogging.PJBlogging.model.VoteType.UPVOTE;
@Service
@AllArgsConstructor

public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;
    @Transactional
    public String vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(()-> new SpringPJBloggingException("Post not found with id: " + voteDto.getPostId()));
        Optional<Vote> votebyPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authService.getCurrentUser());
        if(votebyPostAndUser.isPresent() &&
                votebyPostAndUser.get().getVoteType() == voteDto.getVoteType()) {
            //throw new SpringPJBloggingException("You have already voted for this post");
            return "You have already voted for this post";
        }
        if(UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount()+1);
        }else {
            post.setVoteCount(post.getVoteCount()-1);
        }
        User user = authService.getCurrentUser();
        voteRepository.save(voteMapper.mapToVote(voteDto,post,user));
        postRepository.save(post);
        return "Thank you for voting";
    }
}
