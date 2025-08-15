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
import org.springframework.web.filter.OncePerRequestFilter;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.security.CustomUserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/api/member/login") || path.equals("/api/member/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

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

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }
}
