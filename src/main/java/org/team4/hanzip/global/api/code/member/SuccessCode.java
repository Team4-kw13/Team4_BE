package org.team4.hanzip.global.api.code.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.SuccessResultCode;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	LOGIN_SUCCESS(HttpStatus.OK, "로그인이 성공적으로 완료되었습니다."),
	SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
	LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 완료되었습니다."),
	ACCOUNT_DELETED(HttpStatus.OK, "회원탈퇴가 완료되었습니다."),
	OK(HttpStatus.OK, "요청이 성공적으로 완료되었습니다.");

	private final HttpStatus status;
	private final String message;
}
