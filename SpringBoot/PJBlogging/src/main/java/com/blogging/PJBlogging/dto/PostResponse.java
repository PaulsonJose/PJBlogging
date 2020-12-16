package com.blogging.PJBlogging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subblogName;
    private Integer commentCount;
    private String duration;
    private Integer voteCount;
    private boolean upVote;
    private boolean downVote;
}
