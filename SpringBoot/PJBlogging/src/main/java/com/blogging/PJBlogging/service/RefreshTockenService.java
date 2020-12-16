package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.model.RefreshTocken;
import com.blogging.PJBlogging.repository.RefreshTockenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTockenService {
    private final RefreshTockenRepository refreshTockenRepository;

    public RefreshTocken generateRefreshTocken(){
        RefreshTocken refreshTocken = new RefreshTocken();
        refreshTocken.setTocken(UUID.randomUUID().toString());
        refreshTocken.setCreatedDate(Instant.now());
        return refreshTockenRepository.save(refreshTocken);
    }

    public void validateRefreshTocken(String tocken) {
        refreshTockenRepository.findByTocken(tocken)
                .orElseThrow(()->new SpringPJBloggingException("Invalid refresh Tocken."));
    }
    public void deleteRefreshTocken(String tocken) {
        refreshTockenRepository.deleteByTocken(tocken);
    }
}
