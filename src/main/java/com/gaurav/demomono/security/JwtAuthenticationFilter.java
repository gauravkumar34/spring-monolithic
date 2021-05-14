package com.gaurav.demomono.security;

import com.gaurav.demomono.dto.LoginRequest;
import com.gaurav.demomono.exception.SpringException;
import com.gaurav.demomono.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.*;
import java.time.Instant;
import java.util.Date;

import static java.util.Date.from;

@Service
public class JwtAuthenticationFilter {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;


    public String generatorJwtToken(LoginRequest loginRequest) {
        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(loginRequest.getUsername()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "TopSecret").compact();
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token",jwtToken);
        return jwtToken;

//        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(principal.getUsername())
//                .setIssuedAt(from(Instant.now()))
//                .signWith(SignatureAlgorithm.HS256, "TopSecret")
//                .setExpiration(java.sql.Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
//                .compact();
    }
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
