package com.amazonaws.http;

public class RemoveBenchmarkResponse {
	int statusCode;
	String errorMessage;

	public RemoveBenchmarkResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public String toString() {
		if (statusCode == 200) {  
			return "RemoveBenchmark()";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}
}