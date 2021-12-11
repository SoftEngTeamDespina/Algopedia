package com.amazonaws.http;

public class RemoveClassificationResponse {
	int statusCode;
	String errorMessage;

	public RemoveClassificationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "RemoveClassification()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}