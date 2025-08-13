package org.team4.hanzip.global.api.code.member;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ErrorResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	INVALID_MEMBER(false, HttpStatus.FORBIDDEN, "사용자 정보가 올바르지 않습니다."),
	NOT_EXISTENT_MEMBER(false, HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다.");

	private final boolean success;
	private final HttpStatus status;
	private final String message;
}
