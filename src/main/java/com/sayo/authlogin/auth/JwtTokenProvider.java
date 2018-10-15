package com.sayo.authlogin.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private AuthParameters authParameters;

    /**
     * Generate token for user login.
     *
     * @param authentication
     * @return return a token string.
     */
    public String createJwtToken(Authentication authentication) {
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
                .signWith(SignatureAlgorithm.HS512, authParameters.getJwtTokenSecret())
                .compact();

        return token;
    }

    /**
     * validate token eligible.
     * if Jwts can parse the token string and no throw any exception, then the token is eligible.
     *
     * @param token a jws string.
     */
    public boolean validateToken(String token) {
        String VALIDATE_FAILED = "validate failed : ";
        //ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException
        try {
            Jwts.parser().setSigningKey(authParameters.getJwtTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception ex){
            logger.error(VALIDATE_FAILED + ex.getMessage());
            return false;
        }
    }
}
