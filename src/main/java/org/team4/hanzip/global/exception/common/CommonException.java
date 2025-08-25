package org.team4.hanzip.global.exception.common;

import lombok.Getter;
import org.team4.hanzip.global.api.code.ErrorResultCode;

@Getter
public abstract class CommonException extends RuntimeException {
	private final ErrorResultCode errorResultCode;

	protected CommonException(ErrorResultCode errorResultCode) {
		super(errorResultCode.getMessage());
		this.errorResultCode = errorResultCode;
	}
}
