package org.team4.hanzip.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.member.ErrorCode;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final JwtValidator jwtValidator;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/api/member/login") || path.equals("/api/member/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtValidator.resolveToken(request);
        if(!(accessToken != null && jwtValidator.validateToken(accessToken))) {
            response.getWriter().write(objectMapper.writeValueAsString(ErrorCode.INVALID_MEMBER));
            return;
        }

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //
        if(memberRepository.findById((Long)authentication.getPrincipal()).isEmpty()) {
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.failure(ErrorCode.MEMBER_NOT_FOUND)));
            return;
        }
        //

        filterChain.doFilter(request, response);
    }


}
