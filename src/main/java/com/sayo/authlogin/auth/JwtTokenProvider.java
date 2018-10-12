package com.sayo.authlogin.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private AuthParameters authParameters;

    /**
     * Generate token for user login.
     * @param authentication
     * @return return a token string.
     */
    public String createJwtToken(Authentication authentication){
        //user name
        String userName = ((org.springframework.security.core.userdetails.User) authentication
                .getPrincipal()).getUsername();
        //expire time
        Date expireTime = new Date(System.currentTimeMillis() + authParameters.getTokenExpiredMs());
        //create date
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(expireTime)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512,authParameters.getJwtTokenSecret())
                .compact();

        return token;
    }

    public
}
