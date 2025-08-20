package org.team4.hanzip.domain.member.dto.signup;

public record SignUpResponseDTO (
    long memberId,
    String loginId,
    String nickname
){}
