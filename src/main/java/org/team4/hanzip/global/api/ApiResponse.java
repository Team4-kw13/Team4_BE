package org.team4.hanzip.global.api;

import org.team4.hanzip.global.api.code.ResultCode;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse<T>(
		boolean success,
		int statusCode,
		String message,
		@JsonInclude(value = JsonInclude.Include.NON_NULL)
		T data
) {
	public static <T> ApiResponse<T> success(ResultCode successCode, T data) {
		return new ApiResponse<>(
				successCode.isSuccess(),
				successCode.getStatus().value(),
				successCode.getMessage(),
				data);
	}

	public static <T> ApiResponse<T> success(ResultCode successCode) {
		return success(successCode, null);
	}

	public static <T> ApiResponse<T> failure(ResultCode errorCode) {
		return new ApiResponse<>(
				errorCode.isSuccess(),
				errorCode.getStatus().value(),
				errorCode.getMessage(),
				null);
	}
}
