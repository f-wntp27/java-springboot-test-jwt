package com.sut.reg.server.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
    
    public String generateToken(String subject) {
        return Jwts.builder()
                .setId("test")
                .setSubject(subject)
                .setIssuer("issuer")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(JWTConstants.key)
                .compact();

    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWTConstants.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(String token, String username) {
        String tokenUsername = getClaims(token).getSubject();
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

}
