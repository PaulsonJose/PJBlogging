package com.blogging.PJBlogging.dto;

import com.blogging.PJBlogging.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
