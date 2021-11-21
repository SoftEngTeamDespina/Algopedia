package com.amazonaws.http;

import com.amazonaws.entities.User;

public class LoginResponse {
	
	int httpStatusCode;
	String logMsg;
	
	public LoginResponse() {}
	
	public LoginResponse( int httpStatusCode, String logMsg) {
		
		this.httpStatusCode = httpStatusCode;
		this.logMsg = logMsg;
	}
	
	
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
	
	@Override
	public String toString() {
		return "RegisterUserResponse [user="+
				", httpCode=" + this.httpStatusCode + 
				", log=" + this.logMsg + "]";
	}
	

}
