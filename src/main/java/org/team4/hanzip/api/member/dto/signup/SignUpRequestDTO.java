package org.team4.hanzip.api.member.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.team4.hanzip.domain.member.entity.Member;

@AllArgsConstructor
@Getter
public class SignUpRequestDTO {
    private String nickname;
    private String loginId;
    private String password;

    public Member toEntity() {
        return new Member.Builder()
                .nickname(this.nickname)
                .loginId(this.loginId)
                .password(this.password)
                .build();
    }
}
