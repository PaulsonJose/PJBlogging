package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.dto.AuthenticationResponse;
import com.blogging.PJBlogging.dto.RefreshTockenRequest;
import com.blogging.PJBlogging.dto.RegisterRequest;
import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.model.NotificationEmail;
import com.blogging.PJBlogging.model.User;
import com.blogging.PJBlogging.model.VerificationTocken;
import com.blogging.PJBlogging.repository.UserRepository;
import com.blogging.PJBlogging.repository.VerificationTockenRepository;
import com.blogging.PJBlogging.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor

public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTockenRepository verificationTockenRepository;
    private final MailService mailService;
    private final RefreshTockenService refreshTockenService;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        //The user Details received from the user creation request will be assigned to user entity and save.
        // The user is not enabled as of now because the email needs to be verified.
        User user = new User();
        user.setUserName(registerRequest.getUser());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        if(Strings.isBlank(registerRequest.getUserRole())) {
            registerRequest.setUserRole("ROLE_USER");
        }
        user.setUserRole(registerRequest.getUserRole());
        userRepository.save(user);
        String vTocken = generateVerificationTocken(user);
        String mailBody = "<p>Thank you for Signing up for PJ Blogging.<p>  " +
                "<p>Please click on the <a href = 'http://localhost:8081/api/auth/accountVerification/" + vTocken + "'> link<a> " +
                "to activate your account.<p>";
        mailService.sendEmail(new NotificationEmail("Please activate your account", user.getEmail(), mailBody));
    }

    private String generateVerificationTocken(User user) {
        //A Verification Tocken will be generated using RandonUUID and save for enabling user after verifying email later.
        String verificationTocken = UUID.randomUUID().toString();
        VerificationTocken verificationTocken1 = new VerificationTocken();
        verificationTocken1.setUser(user);
        verificationTocken1.setTocken(verificationTocken);
        verificationTockenRepository.save(verificationTocken1);
        return verificationTocken;
    }


    public void verifyAccnt(String tocken, Model model) {
        //Search the tocken in the Verification Tocken Entity. If not available throw error.
        Optional<VerificationTocken> verificationTocken = verificationTockenRepository.findByTocken(tocken);
        verificationTocken.orElseThrow(() ->new SpringPJBloggingException("Tocken cannot be found!"));
        fetchUserAndEnableTocken(verificationTocken.get());
    }

    @Transactional
    private void fetchUserAndEnableTocken(VerificationTocken verificationTocken) {
        //From Verification Tocken Entity, check if the user is present in DB and enable the user if found.
        String userName = verificationTocken.getUser().getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(()-> new SpringPJBloggingException("User not found! " + userName));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        /*org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(principal.getUsername()).orElseThrow(() ->
        new SpringPJBloggingException("Username not found." + principal.getUsername()));
         */

        Authentication loggedinUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUserName(loggedinUser.getName()).orElseThrow(()-> new SpringPJBloggingException("Username not found." + loggedinUser.getName()));

    }
    public AuthenticationResponse refreshTocken(RefreshTockenRequest refreshTockenRequest) {
        refreshTockenService.validateRefreshTocken(refreshTockenRequest.getRefreshTocken());
        String tocken = jwtUtil.generateTockenWithUserName(refreshTockenRequest.getUserName());
        return AuthenticationResponse.builder().username(refreshTockenRequest.getUserName())
                .refreshTocken(refreshTockenRequest.getRefreshTocken())
                .expiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .tocken(tocken)
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
