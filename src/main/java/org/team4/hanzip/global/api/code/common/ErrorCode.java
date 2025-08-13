package org.team4.hanzip.global.api.code.common;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ResultCode {
	INVALID_HTTP_METHOD(false, HttpStatus.BAD_REQUEST, "올바르지 않은 http 메서드입니다."),
	INVALID_REQUEST_PATH(false, HttpStatus.BAD_REQUEST, "올바르지 않은 요청 경로입니다."),
	INVALID_REQUEST_CONTENT(false, HttpStatus.BAD_REQUEST, "올바르지 않은 요청 데이터입니다."),
	REQUIRED_ARGS_NOT_FOUND(false, HttpStatus.BAD_REQUEST, "필수 요청값이 존재하지 않습니다.");

	private final boolean success;
	private final HttpStatus status;
	private final String message;
}
