package com.amazonaws.http;

import java.util.LinkedList;

import com.amazonaws.entities.User;
import com.amazonaws.entities.UserAction;

public class GetUserActionsResponse {
	LinkedList<UserAction> actions;
	int httpStatusCode;
	String logMsg;
	
	public GetUserActionsResponse() {}
	
	public GetUserActionsResponse(LinkedList<UserAction> actions, int httpStatusCode, String logMsg) {
		this.actions = actions;
		this.httpStatusCode = httpStatusCode;
		this.logMsg = logMsg;
	}
	
	
	public LinkedList<UserAction> getUsername() {
		return actions;
	}

	public void setUserAction(LinkedList<UserAction> linkedList) {
		this.actions = linkedList;
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
		return "RegisterUserResponse [actions="+
				", httpCode=" + this.httpStatusCode + 
				", log=" + this.logMsg + "]";
	}
	

}
