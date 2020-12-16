package com.blogging.PJBlogging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String refreshTocken;
    private Instant expiresAt;
    private String tocken;
    private String username;
}
