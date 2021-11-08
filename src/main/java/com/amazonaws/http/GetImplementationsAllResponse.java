package com.amazonaws.http;

import java.util.ArrayList;

import com.amazonaws.entities.Implementation;

public class GetImplementationsAllResponse {
	ArrayList<Implementation> implementations;
	int statusCode;
	String errorMessage;
	
	public GetImplementationsAllResponse(ArrayList<Implementation> implementations, int statusCode){
		this.implementations = implementations;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public GetImplementationsAllResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "GetImplementations(" + implementations + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
