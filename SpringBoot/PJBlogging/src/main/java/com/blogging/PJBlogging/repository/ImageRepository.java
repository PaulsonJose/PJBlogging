package com.blogging.PJBlogging.repository;

import com.blogging.PJBlogging.model.ImageModel;
import com.blogging.PJBlogging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByImgName(String name);

    Optional<ImageModel> findByUser(User user);
    Optional<ImageModel> findByUserAndUsageStr(User user, String usageStr);
}
