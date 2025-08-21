package org.team4.hanzip.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.security.util.WhiteList;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtValidator jwtValidator;
	private final JwtResolver jwtResolver;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		if (WhiteList.isPermitted(request.getRequestURI(), HttpMethod.valueOf(request.getMethod()))) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessTokenWithBearer = request.getHeader("Authorization");

		if (!jwtValidator.isValid(accessTokenWithBearer)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.failure(ErrorCode.INVALID_MEMBER)));
			return;
		}

		registerAuthentication(accessTokenWithBearer.substring(7));

		filterChain.doFilter(request, response);
	}

	private void registerAuthentication(String accessToken) {
		long memberId = jwtResolver.getMemberId(accessToken);

		Authentication authentication = new UsernamePasswordAuthenticationToken(memberId, null, Collections.emptyList());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}