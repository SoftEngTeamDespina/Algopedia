package com.amazonaws.http;

import java.util.LinkedList;

import com.amazonaws.entities.User;

public class GetAllUsersResponse {
	LinkedList<String> username;
	int httpStatusCode;
	String logMsg;
	
	public GetAllUsersResponse() {}
	
	public GetAllUsersResponse(LinkedList<String> username, int httpStatusCode, String logMsg) {
		this.username = username;
		this.httpStatusCode = httpStatusCode;
		this.logMsg = logMsg;
	}
	
	
	public LinkedList<String> getUsername() {
		return username;
	}

	public void setUsername(LinkedList<String> username) {
		this.username = username;
	}

	public String getLogMsg() {
		return logMsg;
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
