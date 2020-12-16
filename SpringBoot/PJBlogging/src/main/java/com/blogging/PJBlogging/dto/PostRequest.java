package com.blogging.PJBlogging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostRequest {
    private Long post_Id;
    private String subblogName;
    private String post_Name;
    private String url;
    private String description;
}
