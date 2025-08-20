package org.team4.hanzip.domain.member.dto.signup;

import org.team4.hanzip.domain.member.entity.Member;

public record SignUpRequestDTO(
     String nickname,
     String loginId,
     String password) {

    public Member toEntity(final String encodedPassword) {
        return Member.builder()
                .nickname(nickname)
                .loginId(loginId)
                .password(encodedPassword)
                .build();
    }
}
