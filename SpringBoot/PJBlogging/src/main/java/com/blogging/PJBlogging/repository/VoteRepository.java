package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
