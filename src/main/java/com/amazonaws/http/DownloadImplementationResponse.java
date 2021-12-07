package com.amazonaws.http;

import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.entities.Implementation;

public class DownloadImplementationResponse {	
	int statusCode;
	String errorMessage;
	

	public DownloadImplementationResponse(Implementation implementation, int statusCode){
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public DownloadImplementationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "DownloadImplementation";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
