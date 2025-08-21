package org.team4.hanzip.global.exception.contract;

import org.team4.hanzip.global.api.code.contract.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class InvalidFileExtensionException extends CommonException {
	public InvalidFileExtensionException() {
		super(ErrorCode.INVALID_FILE_EXTENSION);
	}
}
