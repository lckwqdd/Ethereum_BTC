package com.mirko.androidutil.exception;

public class VersionException extends Exception{
	
	private String message;
	
	public VersionException() {
		
	}
	
	public VersionException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		String result = "不支持的版本格式。版本号格式必须为 1.0.0样式";
		if(message != null) {
			result += "  >  " + message;
		}
		return result;
	}
}
