package com.amazonaws.entities;

import java.io.File;
import java.util.LinkedList;

public class Implementation {
	private String implementationID;
	private Language language;
	private LinkedList<Benchmark> benchmarks;
	private File code;
	
	
	public String getImplementationID() {
		return implementationID;
	}
	public void setImplementationID(String implementationID) {
		this.implementationID = implementationID;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public LinkedList<Benchmark> getBenchmarks() {
		return benchmarks;
	}
	public void setBenchmarks(LinkedList<Benchmark> benchmarks) {
		this.benchmarks = benchmarks;
	}
	public File getCode() {
		return code;
	}
	public void setCode(File code) {
		this.code = code;
	}
	
}
