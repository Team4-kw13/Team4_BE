package org.team4.hanzip.domain.member.dto.response;

import org.team4.hanzip.domain.member.entity.Member;

public record MyPageResponseDto(
		String nickname
) {
	public static MyPageResponseDto from(Member member) {
		return new MyPageResponseDto(member.getNickname());
	}
}
