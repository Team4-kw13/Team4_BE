package org.team4.hanzip.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.Long.parseLong;

@Component
public class JwtValidator {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey key;

    @PostConstruct
    void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);
        Long memberId = parseLong(claims.getBody().getSubject());
        return new UsernamePasswordAuthenticationToken(memberId, null, null);
    }
}
