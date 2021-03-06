package com.amazonaws.http;

import com.amazonaws.entities.User;

public class LoginResponse {
	User username;
	int httpStatusCode;
	String logMsg;
	
	public LoginResponse() {}
	
	public LoginResponse(User username, int httpStatusCode, String logMsg) {
		this.username = username;
		this.httpStatusCode = httpStatusCode;
		this.logMsg = logMsg;
	}
	
	
	public User getUsername() {
		return username;
	}
	

	public void setUsername(User username) {
		this.username = username;
	}

	public void User(User username) {
		this.username = username;
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
