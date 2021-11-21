package com.amazonaws.http;

public class RemoveImplementationResponse {
	String implemantationID;
	int statusCode;
	String errorMessage;

	public RemoveImplementationResponse(String implemantationID, int statusCode){
		this.implemantationID = implemantationID;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public RemoveImplementationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}

	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateImplementation(" + implemantationID + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
