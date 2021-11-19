package com.amazonaws.entities;

import java.sql.Date;

public class UserAction {
	
	private String userActionID;
	private String authorID;
	private String action;
	private String timeStamp;
	
	public UserAction(String userActionID, String authorID, String action, String timeStamp) {
		this.userActionID = userActionID;
		this.authorID = authorID;
		this.action = action;
		this.timeStamp = timeStamp;
		
	}
	
	public UserAction(String authorID, String action, String timeStamp) {
		this.authorID = authorID;
		this.action = action;
		this.timeStamp = timeStamp;
		
	}
	
	public String getUserActionID() {
		return userActionID;
	}
	public void setUserActionID(String userActionID) {
		this.userActionID = userActionID;
	}
	public String getAuthorID() {
		return authorID;
	}
	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
