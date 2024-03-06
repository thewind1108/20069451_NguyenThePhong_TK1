package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTservice {
    private static final String SECRET_KEY = "your_secret_key"; // Chỉnh sửa: Thay đổi SECRET_KEY thành một giá trị bí mật
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateJWT(String id) {
        Instant now = Instant.now();
        Date expiration = Date.from(now.plusSeconds(3600)); // Token hết hạn trong 1 giờ

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(Date.from(now))
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Claims parseJWT(String jwt) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        return claimsJws.getBody();
    }
}
