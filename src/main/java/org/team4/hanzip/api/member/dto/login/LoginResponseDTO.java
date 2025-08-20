package org.team4.hanzip.api.member.dto.login;

public record LoginResponseDTO (
    String accessToken,
    String refreshToken,
    UserInfo userinfo
){
    public record UserInfo(
        Long memberId,
        String loginId,
        String nickname
    ){}
}

