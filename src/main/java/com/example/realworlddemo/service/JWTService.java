package com.example.realworlddemo.service;

import com.example.realworlddemo.models.Users;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;

@Service
public class JWTService {
    public static final String JWT_KEY = "dL9nLwtKCjFfgj9gwuhJwqw4R4iepkfzC5XGdLfpyFTKuWDcpGCeZDBghzitHUjn";

    public static  final int JWT_EXPIRED_AT = 1000 * 60 * 60 * 24 * 7;

    private final Key key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));

    JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

    public String createJwt(Users users){
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_AT))
                .signWith(key)
                .compact();
    }

    public String decodeJwt(String jwt){
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }
}
