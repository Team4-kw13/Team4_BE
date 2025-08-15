package org.team4.hanzip.global.api.code.member;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ErrorResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	INVALID_MEMBER(HttpStatus.UNAUTHORIZED, "사용자 정보가 올바르지 않습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다."),
	MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자 ID입니다.");
	private final HttpStatus status;
	private final String message;
}
