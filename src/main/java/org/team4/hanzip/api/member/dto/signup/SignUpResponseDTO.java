package org.team4.hanzip.api.member.dto.signup;

public record SignUpResponseDTO (
    long memberId,
    String loginId,
    String nickname
){}
