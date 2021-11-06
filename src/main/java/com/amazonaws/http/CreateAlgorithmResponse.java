package com.amazonaws.http;

public class CreateAlgorithmResponse {
	String algorithmID;
	int statusCode;
	String errorMessage;

	public CreateAlgorithmResponse(String algorithmID, int statusCode){
		this.algorithmID = algorithmID;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public CreateAlgorithmResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}

	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateAlgorithm(" + algorithmID + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}


}
