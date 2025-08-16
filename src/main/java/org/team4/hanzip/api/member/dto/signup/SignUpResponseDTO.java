package org.team4.hanzip.api.member.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpResponseDTO {
    private long memberId;
    private String loginId;
    private String nickname;
}
