package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.Subblog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubblogRepository extends JpaRepository<Subblog, Long> {

Optional <Subblog> findById(Long id);
Optional <Subblog> findByName(String name);
Optional <Subblog> findByUser(String user);
}
