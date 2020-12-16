package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.dto.SubblogDto;
import com.blogging.PJBlogging.service.SubblogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/pjsubblog")
@AllArgsConstructor
public class SubblogController {
    private final SubblogService subblogService;

    @PostMapping
    public SubblogDto create(@RequestBody @Valid SubblogDto subblogDto) {
        subblogService.save(subblogDto);
        return subblogDto;
    }
    @GetMapping
    public List<SubblogDto> getAllSubblogs() {
        return subblogService.getAll();
    }

    @GetMapping("/{id}")
    public SubblogDto getSubblog(@PathVariable Long id) {
        return subblogService.getSubblog(id);
    }

}
