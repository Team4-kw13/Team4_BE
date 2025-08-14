package org.team4.hanzip.global.exception.common;

import org.team4.hanzip.global.api.code.ErrorResultCode;

import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {
	private final ErrorResultCode errorResultCode;

	protected CommonException(ErrorResultCode errorResultCode) {
		super(errorResultCode.getMessage());
		this.errorResultCode = errorResultCode;
	}
}
