package com.channel_chat_service.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String getJwtFromHeader(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //creating new token after checking a valid user
    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String token = Jwts.builder().subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    //getting the username/ userdetails from a valid bearer token given at user end
    public String getUserNameFromJwtToken(String token) {
        String username = Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
        return username;
    }

    public boolean validateJwtToken(String authToekn) {
        try {
            System.out.println("Validating Auth Token");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToekn);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired: {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token is not supported: {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
