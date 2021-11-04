package com.amazonaws.http;

public class CreateAlgorithmRequest {
	String name;
	String ID;
	String description;
	String classificationID;
	
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	
	public String getID() {return this.ID;}
	public void setId(String ID) {this.ID = ID;}
	
	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getClassificationID() {return this.classificationID;}
	public void setClassificationID(String classificationID) {this.classificationID = classificationID;}
	
	public String toString() {
		return "Create Algorithm: " + ID + ", " + name + ", " + description + ", " + classificationID;
	}
	
	public CreateAlgorithmRequest(String name, String ID, String description, String classificationID){
		this.name = name;
		this.ID = ID;
		this.description = description;
		this.classificationID = classificationID;
	}
	
	public CreateAlgorithmRequest() {
		
	}

}
