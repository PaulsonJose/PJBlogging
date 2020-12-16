package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.dto.VoteDto;
import com.blogging.PJBlogging.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor

public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto){
        String resp = voteService.vote(voteDto);
        //return new ResponseEntity<String>(HttpStatus.OK);
        return status(HttpStatus.OK).body( resp );
    }
}
