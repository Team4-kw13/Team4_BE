package org.team4.hanzip.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequestDTO {
    private String nickname;
    private String loginId;
    private String password;
}
