package com.amazonaws.http;

public class ReclassifyAlgorithmResponse {
	int statusCode;
	String errorMessage;

	public ReclassifyAlgorithmResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "ReclassifyAlgorithm()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}