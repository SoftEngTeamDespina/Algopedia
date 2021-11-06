package com.amazonaws.http;

import com.amazonaws.entities.User;

public class RegisterUserResponse {
	User user;
	int httpStatusCode;
	String logMsg;
	
	public RegisterUserResponse() {}
	
	public RegisterUserResponse(User user, int httpStatusCode, String logMsg) {
		this.user = user;
		this.httpStatusCode = httpStatusCode;
		this.logMsg = logMsg;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
	
	@Override
	public String toString() {
		return "RegisterUserResponse [user=" + this.user +
				", httpCode=" + this.httpStatusCode + 
				", log=" + this.logMsg + "]";
	}
	

}
