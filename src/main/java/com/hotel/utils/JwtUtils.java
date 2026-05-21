package com.hotel.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtils {
  //  @Value(value = "${SECRET}")
  private String SECRET = "1234ABCDEFGHakdlfjalkdfjaljfdkldajflkadjdas";

  public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}
