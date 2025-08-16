package org.team4.hanzip.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.team4.hanzip.domain.member.entity.Member;
import org.team4.hanzip.global.security.CustomUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.Long.parseLong;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;
    private SecretKey key;

    @PostConstruct
    void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(Member member) {
        Claims claims = Jwts.claims()
                .setSubject(member.getId().toString())
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Claims claims = Jwts.claims()
                .setSubject(member.getId().toString())
                .build();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);
        Long memberId = parseLong(claims.getBody().getSubject());

//        CustomUserDetails userDetails = new CustomUserDetails(memberId);
        return new UsernamePasswordAuthenticationToken(memberId, null, null);

    }

}
