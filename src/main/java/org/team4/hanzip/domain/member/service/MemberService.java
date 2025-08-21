package org.team4.hanzip.domain.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team4.hanzip.domain.member.dto.request.LoginRequestDto;
import org.team4.hanzip.domain.member.dto.response.MyPageResponseDto;
import org.team4.hanzip.domain.member.dto.request.SignUpRequestDto;
import org.team4.hanzip.domain.member.dto.response.TokenResponseDto;
import org.team4.hanzip.domain.member.entity.Member;
import org.team4.hanzip.domain.member.repository.MemberRepository;
import org.team4.hanzip.global.exception.member.InvalidLoginInformationException;
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
	public void signUp(final SignUpRequestDto signUpRequestDTO) {
		if (memberRepository.existsByLoginId(signUpRequestDTO.loginId())) {
			throw new MemberAlreadyExistException();
		}

		memberRepository.save(signUpRequestDTO.toEntity(passwordEncoder));
	}

	public TokenResponseDto login(final LoginRequestDto loginRequestDto) {
		Member member = memberRepository.findMemberByLoginId(loginRequestDto.loginId())
				.orElseThrow(MemberNotFoundException::new);

		if (!passwordEncoder.matches(loginRequestDto.password(), member.getPassword())) {
			throw new InvalidLoginInformationException();
		}

		return TokenResponseDto.of(
				jwtProvider.generateAccessToken(member),
				jwtProvider.generateRefreshToken(member)
		);
	}

	public MyPageResponseDto getMyPageInfo(final long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
		return MyPageResponseDto.from(member);
	}
}
