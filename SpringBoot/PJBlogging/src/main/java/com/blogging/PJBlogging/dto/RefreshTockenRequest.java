package com.blogging.PJBlogging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RefreshTockenRequest {
    @NotBlank
    private String refreshTocken;
    private String userName;
}
