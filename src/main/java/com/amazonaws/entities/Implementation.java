package com.amazonaws.entities;

import java.io.File;
import java.util.LinkedList;

public class Implementation {
	private String implementationID;
	private String language;
	private LinkedList<Benchmark> benchmarks;
	private String filename;
	private String algorithmID;
	private String stamp;
	
	public Implementation() {}
	
	public Implementation(String stamp,String language, String filename, String algorithmID) {
		this.stamp = stamp;
		this.language = language;
		this.filename = filename;
		this.algorithmID = algorithmID;
		
	}
	
	public Implementation(String implementationID, String stamp,String language, String filename, String algorithmID) {
		this.stamp = stamp;
		this.implementationID = implementationID;
		this.language = language;
		this.filename = filename;
		this.algorithmID = algorithmID;
		
		
	}
	
	
	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public String getImplementationID() {
		return implementationID;
	}
	public void setImplementationID(String implementationID) {
		this.implementationID = implementationID;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public LinkedList<Benchmark> getBenchmarks() {
		return benchmarks;
	}
	public void setBenchmarks(LinkedList<Benchmark> benchmarks) {
		this.benchmarks = benchmarks;
	}
	public String getFileName() {
		return filename;
	}
	public void setFileName(String filename) {
		this.filename = filename;
	}
	
	public String getAlgorithmID() {
		return algorithmID;
	}
	public void setAlgorithmID(String algorithmID) {
		this.algorithmID = algorithmID;
	}
	
}
