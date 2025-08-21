package org.team4.hanzip.global.security.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.config.CorsConfig;
import org.team4.hanzip.global.security.jwt.JwtFilter;
import org.team4.hanzip.global.security.jwt.JwtProvider;

import java.security.SecureRandom;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final CorsConfig corsConfig;
	private final JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers("/**").permitAll()
								.anyRequest().authenticated()
				)
				.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
				.addFilterBefore(
						jwtFilter,
						UsernamePasswordAuthenticationFilter.class
				);

		return http.build();
	}
}
