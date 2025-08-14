package org.team4.hanzip.global.exception.member;

import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class InvalidMemberException extends CommonException {
	public InvalidMemberException() {
		super(ErrorCode.INVALID_MEMBER);
	}
}
