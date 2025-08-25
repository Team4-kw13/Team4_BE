package org.team4.hanzip.global.exception.member;

import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class MemberNotFoundException extends CommonException {
	public MemberNotFoundException() {
		super(ErrorCode.MEMBER_NOT_FOUND);
	}
}
