package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.VerificationTocken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationTockenRepository extends JpaRepository<VerificationTocken,Long> {

     public Optional<VerificationTocken> findByTocken(String tocken) ;
}
