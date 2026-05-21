package com.hotel.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtils {
  //  @Value(value = "${SECRET}")
  private final String SHARED_SECRET = "1234ABCDEFGHakdlfjalkdfjaljfdkldajflkadjdas";

  private final SecretKey key = Keys.hmacShaKeyFor(SHARED_SECRET.getBytes(StandardCharsets.UTF_8));

  public static String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(key)
            .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser()
            .verifyWith(SECRET)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }
}
