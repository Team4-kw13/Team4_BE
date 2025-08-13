package org.team4.hanzip.global.exception.common;

import org.team4.hanzip.global.api.code.ResultCode;

import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {
	private final ResultCode resultCode;

	protected CommonException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}
}
