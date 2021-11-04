package com.amazonaws.http;

public class CreateClassificationResponse {
	String classificationID;
	int statusCode;
	String errorMessage;
	
	public CreateClassificationResponse(String classificationID, int statusCode){
		this.classificationID = classificationID;
		this.statusCode = statusCode;
		this.errorMessage = "";
		
	}
	
	public CreateClassificationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateClassification(" + classificationID + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
