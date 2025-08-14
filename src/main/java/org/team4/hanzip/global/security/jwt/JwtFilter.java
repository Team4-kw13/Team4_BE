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
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.security.CustomUserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = resolveToken((HttpServletRequest) servletRequest);
        if(accessToken != null && jwtProvider.validateToken(accessToken)) {
            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(memberRepository.findById(((CustomUserDetails)authentication.getPrincipal()).getMemberid()).isEmpty()) {
                HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
                httpResponse.setStatus(401);
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.getWriter().write(
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
            HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;
            httpResponse.setStatus(401);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write(
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

        filterChain.doFilter(servletRequest, servletResponse);
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
