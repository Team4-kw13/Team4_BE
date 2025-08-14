package org.team4.hanzip.global.api.code;

public interface ErrorResultCode extends ResultCode {
	default boolean isSuccess(){
		return false;
	}
}
