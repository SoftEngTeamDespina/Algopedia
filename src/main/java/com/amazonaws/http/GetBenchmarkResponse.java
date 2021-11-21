package com.amazonaws.http;

import java.util.LinkedList;

import com.amazonaws.entities.Benchmark;

public class GetBenchmarkResponse {
	LinkedList<Benchmark> benchmarks;
	int statusCode;
	String errorMessage;

	public GetBenchmarkResponse(LinkedList<Benchmark> benchmarks, int statusCode){
		this.benchmarks = benchmarks;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public GetBenchmarkResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}

	public String toString() {
		if (statusCode / 100 == 2) {  
			return "benchmarks(" + benchmarks + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
