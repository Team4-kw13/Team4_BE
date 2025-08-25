package org.team4.hanzip.domain.member.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.team4.hanzip.domain.member.entity.Member;

public record SignUpRequestDto(
		String nickname,
		String loginId,
		String password
) {
	public Member toEntity(final PasswordEncoder passwordEncoder) {
		return Member.builder()
				.nickname(nickname)
				.loginId(loginId)
				.password(passwordEncoder.encode(password))
				.build();
	}
}
