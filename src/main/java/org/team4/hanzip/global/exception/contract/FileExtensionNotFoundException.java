package org.team4.hanzip.global.exception.contract;

import org.team4.hanzip.global.api.code.contract.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class FileExtensionNotFoundException extends CommonException {
	public FileExtensionNotFoundException() {
		super(ErrorCode.FILE_EXTENSION_NOT_FOUND);
	}
}
