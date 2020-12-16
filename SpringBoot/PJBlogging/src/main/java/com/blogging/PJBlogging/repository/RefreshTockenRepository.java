package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.RefreshTocken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTockenRepository extends JpaRepository<RefreshTocken, Long> {
    Optional<RefreshTocken> findByTocken(String tocken);

    void deleteByTocken(String tocken);
}
