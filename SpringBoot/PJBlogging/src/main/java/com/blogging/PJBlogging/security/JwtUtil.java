package com.blogging.PJBlogging.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("$jwt.secret")
    private String SECRET_KEY;
    @Value("${jwt.expire.time}")
    private Long jwtExpirationInMillis;

    @Value("${templating.oauth.enable.cors}")
    public String enableCors;

    public Long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }

    public String generateTocken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return createTocken(claims, authentication.getName());
    }

    private String createTocken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public String generateTockenWithUserName(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createTocken(claims, userName);
    }

    public Boolean validateTocken(String tocken, UserDetails userDetails) {
        final String username = extractUsername(tocken);
        return (username.equals(userDetails.getUsername()) && !isTockenExpired(tocken));
    }

    public String extractUsername(String tocken) {
        return extractClaim(tocken, Claims::getSubject);
    }

    public <T> T extractClaim(String tocken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(tocken);
        return claimsResolver.apply(claims);
    }

    private Boolean isTockenExpired(String tocken) {
        return extractExpiration(tocken).before(new Date());
    }

    public Date extractExpiration(String tocken) {
        return extractClaim(tocken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String tocken) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(tocken).getBody();
    }
}
