package org.team4.hanzip.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.security.CustomUserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    /**
     * 요청의 Authorization 헤더에서 Bearer JWT 엑세스 토큰을 검사하여 인증 컨텍스트를 설정하고,
     * 토큰이 없거나 유효하지 않거나 인증된 사용자가 존재하지 않으면 401 JSON 응답을 반환하는 필터 처리.
     *
     * 상세:
     * - Authorization 헤더에서 토큰을 추출하고 jwtProvider로 유효성을 검증한다.
     * - 유효한 토큰이면 jwtProvider로부터 Authentication을 얻어 SecurityContext에 설정한다.
     * - 설정된 인증의 principal로부터 memberId를 추출하여 MemberRepository에서 사용자가 존재하는지 확인한다.
     * - 사용자가 존재하지 않거나 토큰이 없거나 유효하지 않으면 각각의 경우에 맞는 401 응답(JSON 바디 포함)을 클라이언트에 쓰고 필터 처리를 중단한다.
     * - 모든 검증을 통과하면 filterChain.doFilter(request, response)를 호출하여 요청 처리를 계속 진행한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);
        if(accessToken != null && jwtProvider.validateToken(accessToken)) {
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(memberRepository.findById(((CustomUserDetails)authentication.getPrincipal()).getMemberId()).isEmpty()) {
                response.setStatus(401);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                        """
                            {
                                "success": false,
                                "statusCode": "401",
                                "message": "존재하지 않는 유저입니다. 다시 로그인 해주세요",
                                "data": null
                            }
                        """
                );
                return;
            }
        } else {
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    """
                        {
                            "success": false,
                            "statusCode": "401",
                            "message": "유효하지 않은 또는 누락된 엑세스 토큰입니다. 다시 로그인 해주세요",
                            "data": null
                        }
                    """
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 요청 헤더의 Authorization에서 Bearer 토큰을 추출한다.
     *
     * 요청의 "Authorization" 헤더가 "Bearer "로 시작하면 접두사 이후의 토큰 문자열을 반환하고,
     * 그렇지 않으면 null을 반환한다.
     *
     * @param request 토큰을 읽을 HTTP 요청
     * @return Bearer 토큰 문자열 또는 토큰이 없으면 {@code null}
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}
