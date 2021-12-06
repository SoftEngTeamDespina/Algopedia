package com.amazonaws.http;

import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.ProblemInstance;

public class GetProblemInstancesAllResponse {
	LinkedList<ProblemInstance> instances;
	int statusCode;
	String errorMessage;
	

	public GetProblemInstancesAllResponse(LinkedList<ProblemInstance> instances, int statusCode){
		this.instances = instances;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public GetProblemInstancesAllResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  
			return "GetProblemInstances(" + instances + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
