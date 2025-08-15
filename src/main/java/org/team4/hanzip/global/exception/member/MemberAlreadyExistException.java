package org.team4.hanzip.global.exception.member;

import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class MemberAlreadyExistException extends CommonException {
    public MemberAlreadyExistException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
