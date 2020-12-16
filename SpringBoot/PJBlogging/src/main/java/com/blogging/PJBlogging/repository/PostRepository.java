package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.Post;
import com.blogging.PJBlogging.model.Subblog;
import com.blogging.PJBlogging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubblog(Subblog subblog);
    Optional <List<Post>> findByUser(User user);
}
