package com.amazonaws.http;

import java.util.LinkedList;

import com.amazonaws.entities.Classification;

public class GetClassificationResponse {
	LinkedList<Classification> clList;
	int statusCode;
	String errorMessage;
	
	public GetClassificationResponse(int statusCode,LinkedList<Classification> clList){
		this.clList = clList;
		this.statusCode = statusCode;
		this.errorMessage = "";
		
	}
	
	public GetClassificationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateClassification(" + clList + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
