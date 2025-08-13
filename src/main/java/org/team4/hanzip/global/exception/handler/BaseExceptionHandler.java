package org.team4.hanzip.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.ResultCode;

public abstract class BaseExceptionHandler {
	protected <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(ResultCode resultCode) {
		return ResponseEntity.status(resultCode.getStatus())
				.body(ApiResponse.failure(resultCode));
	}
}
