package com.amazonaws.http;

import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.entities.Implementation;

public class GetImplementationResponse {	
	Implementation implementation;
	int statusCode;
	String errorMessage;
	

	public GetImplementationResponse(Implementation implementation, int statusCode){
		this.implementation = implementation;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public GetImplementationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "GetImplementation(" + implementation + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
