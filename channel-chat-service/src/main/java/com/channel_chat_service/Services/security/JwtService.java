package com.channel_chat_service.Services.security;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private  String secretKey;

    private long jwtExpiration;

    public String generateToken(userPrincipal user) {
        return Jwts.builder()
                .setsSubject(user.getUsername))
                .claim("userId", user.getId)
                .setIssuedAt(new Date())
                .setExpiration (new Date(System.currentTimeMillis()) + 86400000))
                .signWith(Keys.hmacShKeyFor(SECRET.getBytes()))
                .compact();
    }

    public String extractUsername (String token){
        return parse(token).get("userId", Long.class);
    }

    public boolean isValid (String token){
        try{
            parse (token);
            return true;
        } catch (Exception e){
            return false;
        }
    private Claims parse (String token){
           return Jwts.parseBuilder()
                   .setSigningKey (SECRET.getBytes())
                   .build()
                   .parseClaimsJwt (token)
                   .getBody();
        }

    }



}
