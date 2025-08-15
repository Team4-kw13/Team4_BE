package org.team4.hanzip.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.exception.member.InvalidMemberException;
import org.team4.hanzip.global.exception.member.MemberIdAlreadyExistException;
import org.team4.hanzip.global.exception.member.MemberNotFoundException;

@RestControllerAdvice
public class MemberExceptionHandler extends BaseExceptionHandler {
	@ExceptionHandler(MemberNotFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleMemberNotFoundException(MemberNotFoundException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(InvalidMemberException.class)
	protected ResponseEntity<ApiResponse<Void>> handleInvalidMemberException(InvalidMemberException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(MemberIdAlreadyExistException.class)
	protected ResponseEntity<ApiResponse<Void>> handleMemberIdAlreadyExistException(MemberIdAlreadyExistException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}
}
