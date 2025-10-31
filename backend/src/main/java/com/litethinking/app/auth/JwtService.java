package com.litethinking.app.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

  @Value("${app.security.jwt-secret}")
  private String secret;

  @Value("${app.security.jwt-expiration-ms}")
  private long expirationMs;

  public String generate(String username, Collection<String> roles) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationMs);
    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
        .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
        .build()
        .parseClaimsJws(token);
  }

  @SuppressWarnings("unchecked")
  public List<String> extractRoles(String token) {
    var claims = parse(token).getBody();
    Object r = claims.get("roles");
    if (r instanceof Collection<?> c) {
      return c.stream().map(Object::toString).collect(Collectors.toList());
    }
    return List.of();
  }
}
