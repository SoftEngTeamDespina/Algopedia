package com.amazonaws.http;

public class MergeClassificationResponse {
	int statusCode;
	String errorMessage;

	public MergeClassificationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "MergeClassification()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}