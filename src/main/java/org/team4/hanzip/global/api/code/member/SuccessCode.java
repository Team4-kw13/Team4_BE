package org.team4.hanzip.global.api.code.member;

import org.springframework.http.HttpStatus;
import org.team4.hanzip.global.api.code.SuccessResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements SuccessResultCode {
	LOGIN_SUCCESS(true, HttpStatus.OK, "로그인이 완료되었습니다."),
	SIGNUP_SUCCESS(true, HttpStatus.CREATED, "회원가입이 완료되었습니다."),
	LOGOUT_SUCCESS(true, HttpStatus.OK, "로그아웃이 완료되었습니다."),
	ACCOUNT_DELETED(true, HttpStatus.OK, "회원탈퇴가 완료되었습니다.");

	private final boolean success;
	private final HttpStatus status;
	private final String message;
}
