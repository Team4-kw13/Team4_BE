package org.team4.hanzip.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.common.ErrorCode;

@RestControllerAdvice
public class CommonExceptionHandler extends BaseExceptionHandler {
	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleNoHandlerFoundException(NoHandlerFoundException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_PATH);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		return buildErrorResponse(ErrorCode.INVALID_HTTP_METHOD);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_CONTENT);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException e) {
		return buildErrorResponse(ErrorCode.MISSING_REQUIRED_PARAMETER);
	}
}
