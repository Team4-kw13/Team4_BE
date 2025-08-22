package org.team4.hanzip.global.exception.contract;

import org.team4.hanzip.global.api.code.contract.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class ImageFileNotFoundException extends CommonException {
	public ImageFileNotFoundException() {
		super(ErrorCode.FILE_NOT_FOUND);
	}
}
