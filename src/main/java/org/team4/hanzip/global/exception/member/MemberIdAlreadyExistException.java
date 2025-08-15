package org.team4.hanzip.global.exception.member;

import org.team4.hanzip.global.api.code.member.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class MemberIdAlreadyExistException extends CommonException {
    public MemberIdAlreadyExistException() {
        super(ErrorCode.MEMBER_ALREADY_EXIST);
    }
}
