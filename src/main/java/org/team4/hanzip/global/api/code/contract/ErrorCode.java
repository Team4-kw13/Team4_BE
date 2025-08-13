package org.team4.hanzip.global.api.code.contract;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ErrorResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	INVALID_ARGUMENT(false, HttpStatus.BAD_REQUEST, "올바르지 않은 요청값입니다."),
	CONTRACT_NOT_FOUND(false, HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다.");

	private final boolean success;
	private final HttpStatus status;
	private final String message;
}
