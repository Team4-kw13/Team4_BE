package org.team4.hanzip.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.api.code.ErrorResultCode;

public abstract class BaseExceptionHandler {
	protected final <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(ErrorResultCode resultCode) {
		return ResponseEntity.status(resultCode.getStatus())
				.body(ApiResponse.failure(resultCode));
	}
}
