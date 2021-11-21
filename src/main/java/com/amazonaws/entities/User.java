package com.amazonaws.entities;

public class User {
	private String username;
	private String passwordHash;
	private Boolean isAdmin;
	
	
	public User(String uname,String passwordHash,Boolean isAdmin) {
		this.username = uname;
		this.passwordHash = passwordHash;
		this.isAdmin = isAdmin;
	}
	
	public User(String uname,String passwordHash) {
		this.username = uname;
		this.passwordHash = passwordHash;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
