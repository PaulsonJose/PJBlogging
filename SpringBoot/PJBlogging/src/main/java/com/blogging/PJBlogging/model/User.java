package com.blogging.PJBlogging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "blog_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "UserName cannot be null")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @Column(columnDefinition = "varchar(100) default 'USER'")
    private String userRole;
    private Instant created;
    private boolean enabled;
}
