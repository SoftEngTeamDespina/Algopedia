package com.amazonaws.http;

public class CreateProblemInstanceResponse {
    int statusCode;
	String errorMessage;
	String instID;

	public CreateProblemInstanceResponse(String instID, int statusCode, String errorMessage){
		this.instID = instID;
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "CreateProblemInstance("+instID+")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	} 
}