package org.team4.hanzip.global.exception.contract;

import org.team4.hanzip.global.api.code.contract.ErrorCode;
import org.team4.hanzip.global.exception.common.CommonException;

public class ContractNotFoundException extends CommonException {
	public ContractNotFoundException() {
		super(ErrorCode.CONTRACT_NOT_FOUND);
	}
}
