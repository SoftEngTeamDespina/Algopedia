package com.amazonaws.entities;

import java.sql.Date;

public class UserAction {
	
	private String userActionID;
	private User authorID;
	private String action;
	private Date timeStamp;
	
	
	public String getUserActionID() {
		return userActionID;
	}
	public void setUserActionID(String userActionID) {
		this.userActionID = userActionID;
	}
	public User getAuthorID() {
		return authorID;
	}
	public void setAuthorID(User authorID) {
		this.authorID = authorID;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
