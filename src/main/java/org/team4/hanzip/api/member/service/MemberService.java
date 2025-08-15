package org.team4.hanzip.api.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team4.hanzip.api.member.dto.LoginRequestDTO;
import org.team4.hanzip.api.member.dto.LoginResponseDTO;
import org.team4.hanzip.api.member.dto.SignUpRequestDTO;
import org.team4.hanzip.domain.member.entity.Member;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.exception.member.InvalidMemberException;
import org.team4.hanzip.global.exception.member.MemberAlreadyExistException;
import org.team4.hanzip.global.exception.member.MemberNotFoundException;
import org.team4.hanzip.global.security.jwt.JwtProvider;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Member signUp(final SignUpRequestDTO signUpRequestDTO) {
        String nickname = signUpRequestDTO.getNickname();
        String loginId = signUpRequestDTO.getLoginId();
        String password = signUpRequestDTO.getPassword();

        Member member = memberRepository.findMemberByLoginId(loginId);
        if (member == null) {
            member = new Member.Builder()
                    .nickname(nickname)
                    .loginId(loginId)
                    .password(password)
                    .build();
            return memberRepository.save(member);
        } else {
            throw new MemberAlreadyExistException();
        }
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        String loginId = requestDTO.getLoginId();
        String password = requestDTO.getPassword();

        Member member = memberRepository.findMemberByLoginId(loginId);
        if(member == null) throw new MemberNotFoundException();
        if(!member.getPassword().equals(password)) throw new InvalidMemberException();

        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);

        return new LoginResponseDTO(member.getId(),
                            member.getLoginId(),
                            member.getNickname(),
                            accessToken,
                            refreshToken
                    );
    }
}
