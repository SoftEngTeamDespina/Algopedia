package com.amazonaws.http;

public class CreateBenchmarkResponse {
	String benchmarkID;
	int statusCode;
	String errorMessage;

	public CreateBenchmarkResponse(String benchmarkID, int statusCode){
		this.benchmarkID = benchmarkID;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public CreateBenchmarkResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}

	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateImplementation(" + benchmarkID + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
