package com.blogging.PJBlogging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubblogDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Integer postCount;
}
