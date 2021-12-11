package com.amazonaws.http;

public class RemoveProblemInstanceResponse {
	int statusCode;
	String errorMessage;

	public RemoveProblemInstanceResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "RemoveProblemInstance()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}