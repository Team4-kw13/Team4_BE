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

    /**
     * 컴포넌트 생성 후 구성된 `secret` 값을 사용해 JWT 서명 및 검증에 사용할 HMAC-SHA 비밀키를 초기화합니다.
     *
     * <p>이 메서드는 {@code @PostConstruct}로 실행되어 프로퍼티가 주입된 후 키를 생성합니다.
     */
    @PostConstruct
    void Init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 지정된 회원 정보를 기반으로 만료 시간이 설정된 JWT 액세스 토큰을 생성합니다.
     *
     * 토큰의 `sub`(subject) 클레임에는 회원의 ID(member.getId()) 문자열이 설정되며,
     * 발급 시간(issuedAt)은 현재 시각, 만료 시간(expiration)은 현재 시각에 구성된
     * accessTokenExpiration 밀리초를 더한 값으로 설정됩니다. 토큰은 클래스에 초기화된
     * 비밀키로 HS256 서명되어 반환됩니다.
     *
     * @param member 액세스 토큰을 생성할 대상 회원 (토큰의 subject로 회원 ID가 사용됨)
     * @return 서명된 JWT 액세스 토큰 문자열
     */
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

    /**
     * 지정된 회원을 위한 JWT 리프레시 토큰을 생성하여 반환합니다.
     *
     * 토큰의 서브젝트(subject)로 회원의 ID(member.getId().toString())를 설정하고,
     * 발행시간(issuedAt)은 현재 시각, 만료시간(expiration)은 구성된 리프레시 토큰 만료 기간(refreshTokenExpiration)을 반영합니다.
     *
     * @param member 토큰을 발급할 대상 회원(토큰의 subject에 회원 ID가 사용됩니다)
     * @return 생성된 JWT 리프레시 토큰 문자열
     */
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

    /**
     * 전달된 JWT 문자열이 유효한 서명과 만료 시간을 가지는지 검사합니다.
     *
     * <p>토큰의 서명을 검증하고 만료일시가 현재 시점보다 이후인지 확인합니다.
     * 파싱 또는 검증 실패 시 false를 반환합니다.</p>
     *
     * @param token 검사할 JWT 문자열
     * @return 유효한 토큰이면 {@code true}, 그렇지 않으면 {@code false}
     */
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

    /**
     * 주어진 JWT 액세스 토큰으로부터 인증 정보를 생성하여 반환합니다.
     *
     * <p>토큰의 subject를 멤버 ID로 해석하여 CustomUserDetails를 생성하고,
     * 이를 포함하는 UsernamePasswordAuthenticationToken을 반환합니다.</p>
     *
     * @param accessToken 멤버 ID가 subject로 설정된 유효한 JWT 액세스 토큰 문자열
     * @return 멤버 ID를 기반으로 한 CustomUserDetails를 principal로 가지는 Authentication 객체
     */
    public Authentication getAuthentication(String accessToken) {
        Jws<Claims> claims = Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken);
        Long memberId = parseLong(claims.getBody().getSubject());

        CustomUserDetails userDetails = new CustomUserDetails(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

}
