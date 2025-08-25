package org.team4.hanzip.global.api.code.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.ErrorResultCode;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements ErrorResultCode {
	INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "올바르지 않은 요청값입니다."),
	CONTRACT_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다."),

	// 이미지 업로드 시 예외
	FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "파일이 존재하지 않습니다."),
	FILE_EXTENSION_NOT_FOUND(HttpStatus.BAD_REQUEST, "파일 확장자가 존재하지 않습니다."),
	INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "올바르지 않은 파일 확장자입니다.");

	private final HttpStatus status;
	private final String message;
}
