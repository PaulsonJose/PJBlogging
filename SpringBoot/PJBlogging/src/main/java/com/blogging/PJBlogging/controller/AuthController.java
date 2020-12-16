package com.blogging.PJBlogging.controller;

import com.blogging.PJBlogging.dto.AuthenticationRequest;
import com.blogging.PJBlogging.dto.AuthenticationResponse;
import com.blogging.PJBlogging.dto.RefreshTockenRequest;
import com.blogging.PJBlogging.dto.RegisterRequest;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.security.JwtUtil;
import com.blogging.PJBlogging.service.AuthService;
import com.blogging.PJBlogging.service.RefreshTockenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTockenService refreshTockenService;

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    //Need to capture the user creation request to save in the user table
    @PostMapping("/signup")

    public ResponseEntity<String> signup(@RequestBody RegisterRequest regRequest) {
        authService.signup(regRequest);
        System.out.println("User Registration Successful.");
        return new ResponseEntity<>("User Registration Successful.", HttpStatus.OK);
    }

    //The registered user needs to be verified for the correctness of the email
    @RequestMapping(method = RequestMethod.GET, path = "/accountVerification/{tocken}")
    public ResponseEntity<String> authenticate(@PathVariable("tocken") String tocken, Model model) {
        authService.verifyAccnt(tocken, model);
        return new ResponseEntity<>("Authentication Success!",HttpStatus.OK);
    }

    //Login to be done in POST request as the credentials to be send in the request body.
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                                        (authenticationRequest.getUsername(),authenticationRequest.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String authenticationTocken = jwtUtil.generateTocken(authentication);
                return AuthenticationResponse.builder()
                        .tocken(authenticationTocken)
                        .refreshTocken(refreshTockenService.generateRefreshTocken().getTocken())
                        .expiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                        .username(authenticationRequest.getUsername())
                        .build();
            //(authenticationTocken,authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {
            throw new SpringPJBloggingException("Incorrect Username / Password.");
        }
    }

    @PostMapping("/refresh/tocken")
    public AuthenticationResponse refreshTocken(@Valid @RequestBody RefreshTockenRequest refreshTockenRequest) {
        return authService.refreshTocken(refreshTockenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTockenRequest refreshTockenRequest){
        refreshTockenService.deleteRefreshTocken(refreshTockenRequest.getRefreshTocken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Tocken deleted successfully");
    }
}
