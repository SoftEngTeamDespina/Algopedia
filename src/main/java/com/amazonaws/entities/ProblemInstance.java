package com.amazonaws.entities;

import java.io.File;

public class ProblemInstance {

	private String problemInstanceID;
	private String name;
	private String description;
	private File dataSet;
	
	
	public String getProblemInstanceID() {
		return problemInstanceID;
	}
	public void setProblemInstanceID(String problemInstanceID) {
		this.problemInstanceID = problemInstanceID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public File getDataSet() {
		return dataSet;
	}
	public void setDataSet(File dataSet) {
		this.dataSet = dataSet;
	}
	
	
}
