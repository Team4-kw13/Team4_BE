package org.team4.hanzip.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team4.hanzip.api.member.dto.login.LoginRequestDTO;
import org.team4.hanzip.api.member.dto.login.LoginResponseDTO;
import org.team4.hanzip.api.member.dto.mypage.MyPageRequestDTO;
import org.team4.hanzip.api.member.dto.mypage.MyPageResponseDTO;
import org.team4.hanzip.api.member.dto.signup.SignUpRequestDTO;
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
        Member member = signUpRequestDTO.toEntity();
        if (!memberRepository.existsByLoginId(member.getLoginId())) {
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

    public MyPageResponseDTO myPage(MyPageRequestDTO requestDTO) {
        Member member = memberRepository.findMemberById(requestDTO.getMemberId());
        if(member == null) throw new MemberNotFoundException();
        else return new MyPageResponseDTO(member.getNickname());
    }
}
