package org.team4.hanzip.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team4.hanzip.api.member.dto.login.LoginRequestDTO;
import org.team4.hanzip.api.member.dto.login.LoginResponseDTO;
import org.team4.hanzip.api.member.dto.mypage.MyPageResponseDTO;
import org.team4.hanzip.api.member.dto.signup.SignUpRequestDTO;
import org.team4.hanzip.domain.member.entity.Member;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.member.InvalidMemberException;
import org.team4.hanzip.global.exception.member.MemberAlreadyExistException;
import org.team4.hanzip.global.exception.member.MemberNotFoundException;
import org.team4.hanzip.global.security.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member signUp(final SignUpRequestDTO signUpRequestDTO) {
        if (memberRepository.existsByLoginId(signUpRequestDTO.loginId())) {
            throw new MemberAlreadyExistException();
        }
        return memberRepository.save(
                signUpRequestDTO.toEntity(
                    passwordEncoder.encode(signUpRequestDTO.password())
                )
        );
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        String loginId = requestDTO.loginId();
        String password = requestDTO.password();

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(MemberNotFoundException::new);
        if(!passwordEncoder.matches(password,member.getPassword())) throw new InvalidMemberException();

        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);

        return new LoginResponseDTO(
                            accessToken,
                            refreshToken,
                            new LoginResponseDTO.UserInfo(
                                    member.getId(),
                                    member.getLoginId(),
                                    member.getNickname()
                            )
                    );
    }

    public MyPageResponseDTO myPage(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return new MyPageResponseDTO(member.getNickname());
    }
}
