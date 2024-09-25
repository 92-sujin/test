package com.example.dkbmcsampleproject1.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey; // 비밀 키 설정

    // JWT 생성
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setIssuer("sujin")
                .setClaims(claims) // cliams 값으로 서명
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효
                .signWith(SignatureAlgorithm.HS512, secretKey) // 키 만들기 (압축)
                .compact();
    }
}
