package org.team4.hanzip.global.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.exception.contract.ContractNotFoundException;

@RestControllerAdvice
public class ContractExceptionHandler extends BaseExceptionHandler {
	@ExceptionHandler(ContractNotFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleContractNotFoundException(ContractNotFoundException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}
}
