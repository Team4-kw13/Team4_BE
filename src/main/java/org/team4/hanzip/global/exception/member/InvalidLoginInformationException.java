package org.team4.hanzip.global.exception.member;

import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class InvalidLoginInformationException extends CommonException {
	public InvalidLoginInformationException() {
		super(ErrorCode.INVALID_LOGIN_INFORMATION);
	}
}
