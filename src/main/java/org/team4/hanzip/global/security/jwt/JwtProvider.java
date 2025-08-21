package org.team4.hanzip.global.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.team4.hanzip.domain.member.entity.Member;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
	private final SecretKey secretKey;

	// @Value("${spring.jwt.expiration.access}")
	// private long accessTokenExpiration;
	//
	// @Value("${spring.jwt.expiration.refresh}")
	// private long refreshTokenExpiration;
	private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14;
	private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 14 * 2;

	public JwtProvider(@Value("${spring.jwt.secret}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(final Member member) {
		return generateToken(member.getId(), ACCESS_TOKEN_EXPIRATION_TIME);
	}

	public String generateRefreshToken(final Member member) {
		return generateToken(member.getId(), REFRESH_TOKEN_EXPIRATION_TIME);
	}

	private String generateToken(final long memberId, final long expiration) {
		return Jwts.builder()
				.claim("memberId", memberId)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(secretKey)
				.compact();
	}
}
