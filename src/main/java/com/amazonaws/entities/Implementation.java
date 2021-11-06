package com.amazonaws.entities;

import java.io.File;
import java.util.LinkedList;

public class Implementation {
	private String implementationID;
	private Language language;
	private LinkedList<Benchmark> benchmarks;
	private String filename;
	private String algorithmID;
	
	public Implementation(Language language, String filename, String algorithmID) {
		this.language = language;
		this.filename = filename;
		this.algorithmID = algorithmID;
		
	}
	
	public Implementation(String implementationID, Language language, String filename, String algorithmID) {
		this.implementationID = implementationID;
		this.language = language;
		this.filename = filename;
		this.algorithmID = algorithmID;
		
	}
	
	
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
