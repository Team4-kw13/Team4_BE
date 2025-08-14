package org.team4.hanzip.global.security.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.security.jwt.JwtFilter;
import org.team4.hanzip.global.security.jwt.JwtProvider;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;

    /**
     * 애플리케이션의 HTTP 보안 필터 체인을 구성하여 {@link SecurityFilterChain} 빈을 생성합니다.
     *
     * <p>구성 내용:
     * <ul>
     *   <li>CSRF 보호 비활성화</li>
     *   <li>세션 관리를 무상태(STATELESS)로 설정</li>
     *   <li>기본적으로 모든 요청을 허용(인증 요구 없음)</li>
     *   <li>JWT 처리용 {@code jwtFilter}를 {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter} 앞에 삽입</li>
     * </ul>
     *
     * @return 구성된 {@link SecurityFilterChain} 빈
     * @throws Exception SecurityFilterChain 구성 또는 빌드 중 오류가 발생하면 전달됩니다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 나머지 요청은 인증 필요
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
