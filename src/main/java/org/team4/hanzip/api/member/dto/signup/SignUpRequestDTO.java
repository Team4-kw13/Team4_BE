package org.team4.hanzip.api.member.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.team4.hanzip.domain.member.entity.Member;

@AllArgsConstructor
@Getter
public class SignUpRequestDTO {
    private String nickname;
    private String loginId;
    private String password;

    public Member toEntity(final String encodedPassword) {
        return new Member.Builder()
                .nickname(this.nickname)
                .loginId(this.loginId)
                .password(encodedPassword)
                .build();
    }
}
