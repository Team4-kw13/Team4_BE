package org.team4.hanzip.global.api;

import org.team4.hanzip.global.api.code.ErrorResultCode;
import org.team4.hanzip.global.api.code.SuccessResultCode;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
		boolean success,
		int statusCode,
		String message,
		@JsonInclude(value = JsonInclude.Include.NON_NULL)
		T data
) {
	public static <T> ApiResponse<T> success(SuccessResultCode successCode, T data) {
		return new ApiResponse<>(
				successCode.isSuccess(),
				successCode.getStatus().value(),
				successCode.getMessage(),
				data);
	}

	public static <T> ApiResponse<T> success(SuccessResultCode successCode) {
		return success(successCode, null);
	}

	public static <T> ApiResponse<T> failure(ErrorResultCode errorCode) {
		return new ApiResponse<>(
				errorCode.isSuccess(),
				errorCode.getStatus().value(),
				errorCode.getMessage(),
				null);
	}
}
