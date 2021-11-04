package com.amazonaws.http;

public class CreateClassificationRequest {
	String name;
	String classificationID;
	String description;
	String superClassification;
	
	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}
	
	public String getClassificationID() {return this.classificationID;}
	public void setClassificationID(String classificationID) {this.classificationID = classificationID;}
	
	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getSuperClassification() {return this.superClassification;}
	public void setSuperClass(String superClassification) {this.superClassification = superClassification;}
	
	public String toString() {
		return "Create Classification: " + classificationID + ", " + name + ", " + description + ", " + superClassification;
	}
	
	public CreateClassificationRequest(String name, String classificationID, String description, String superClassification){
		this.name = name;
		this.classificationID = classificationID;
		this.description = description;
		this.superClassification = superClassification;
	}
	
	public CreateClassificationRequest() {
		
	}
	

}
