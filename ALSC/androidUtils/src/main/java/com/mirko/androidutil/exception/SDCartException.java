package com.mirko.androidutil.exception;

public class SDCartException extends Exception {
	
	public enum SDCartExceptionType {
		/** 不可读 */
		canotRead,
		/** 不可写 */
		canotWrite,
		/** 不存在 */
		notExist, 
		/** 没有挂载 */
		unMount
	}
	
	private SDCartExceptionType type;
	
	public SDCartException(SDCartExceptionType type) {
		this.type = type;
	}
	
	public SDCartExceptionType getSDCartExceptionType() {
		return type;
	}
	
}
