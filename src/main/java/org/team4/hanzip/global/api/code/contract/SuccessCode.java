package org.team4.hanzip.global.api.code.contract;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.SuccessResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	OK(HttpStatus.OK, "요청이 성공적으로 완료되었습니다."),
	CREATED(HttpStatus.CREATED, "데이터가 생성되었습니다.");

	private final HttpStatus status;
	private final String message;
}
