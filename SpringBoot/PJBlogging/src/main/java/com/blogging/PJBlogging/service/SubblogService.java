package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.controller.SubblogController;
import com.blogging.PJBlogging.dto.SubblogDto;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.mapper.SubblogMapper;
import com.blogging.PJBlogging.model.Subblog;
import com.blogging.PJBlogging.repository.SubblogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubblogService {
    private final SubblogRepository subblogRepository;
    private final AuthService authService;
    private final SubblogMapper subblogMapper;

    @Transactional (readOnly = true)
    public List<SubblogDto> getAll() {
        return subblogRepository.findAll().stream()
                .map(subblogMapper::mapSubblogDto)
                .collect(Collectors.toList());
    }

    /*
    private SubblogDto mapToDto(Subblog subblog) {
        return SubblogDto.builder().name(subblog.getName())
                .id(subblog.getId())
                .description(subblog.getDescription())
                .postCount(subblog.getPosts().size()).build();
    }
     */
    @Transactional
    public SubblogDto save(SubblogDto subblogDto) {
        Subblog subblog = subblogRepository.save(subblogMapper.mapSubblog(subblogDto, authService.getCurrentUser()));
        subblogDto.setId(subblog.getId());
        return subblogDto;
    }

    /*private Subblog mapToSubblog(SubblogDto subblogDto) {
        return Subblog.builder().name(subblogDto.getName())
                .description(subblogDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now()).build();
    }*/
    @Transactional
    public SubblogDto getSubblog(Long id) {
        Optional<Subblog> subblog = subblogRepository.findById(id);
        subblog.orElseThrow(()-> new SpringPJBloggingException("Sorry, Subblog not found."));
        return subblogMapper.mapSubblogDto(subblog.get());
    }

    public List<SubblogDto> getSubblogByUser(String username) {
        return subblogRepository.findByUser(username).stream().map(subblogMapper:: mapSubblogDto).collect(Collectors.toList());
    }
}
