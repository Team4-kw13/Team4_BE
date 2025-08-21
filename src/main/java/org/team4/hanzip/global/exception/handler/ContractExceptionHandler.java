package org.team4.hanzip.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.team4.hanzip.global.api.ApiResponse;
import org.team4.hanzip.global.exception.contract.ContractNotFoundException;
import org.team4.hanzip.global.exception.contract.FileExtensionNotFoundException;
import org.team4.hanzip.global.exception.contract.FileNotFoundException;
import org.team4.hanzip.global.exception.contract.InvalidFileExtensionException;

@RestControllerAdvice
public class ContractExceptionHandler extends BaseExceptionHandler {
	@ExceptionHandler(ContractNotFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleContractNotFoundException(ContractNotFoundException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(FileNotFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleFileNotFoundException(FileNotFoundException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(FileExtensionNotFoundException.class)
	protected ResponseEntity<ApiResponse<Void>> handleFileExtensionNotFoundException(FileExtensionNotFoundException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(InvalidFileExtensionException.class)
	protected ResponseEntity<ApiResponse<Void>> handleInvalidFileExtensionException(InvalidFileExtensionException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}
}
