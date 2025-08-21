package org.team4.hanzip.global.exception.contract;

import org.team4.hanzip.global.api.code.ErrorResultCode;
import org.team4.hanzip.global.api.code.contract.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class FileNotFoundException extends CommonException {
	public FileNotFoundException() {
		super(ErrorCode.FILE_NOT_FOUND);
	}
}
