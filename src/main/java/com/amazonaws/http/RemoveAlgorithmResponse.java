package com.amazonaws.http;

public class RemoveAlgorithmResponse {
	int statusCode;
	String errorMessage;

	public RemoveAlgorithmResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "RemoveAlgorithm()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}