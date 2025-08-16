package org.team4.hanzip.api.member.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDTO {
    private Long memberId;
    private String loginId;
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
