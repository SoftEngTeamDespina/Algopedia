package com.amazonaws.entities;

import java.util.LinkedList;

public class Algorithm {
	private String algorithmID;
	private String name;
	private String description;
	private String classificationID;
	private LinkedList<Implementation> implementations;
	private LinkedList<ProblemInstance> problemInstances;
	
	
	
	public Algorithm(String algorithmID, String name, String description, String classificationID) {
		this.algorithmID = algorithmID;
		this.name = name;
		this.description = description;
		this.classificationID = classificationID;
		
	}
	
	public String getAlgorithmID() {
		return algorithmID;
	}
	public void setAlgorithmID(String algorithmID) {
		this.algorithmID = algorithmID;
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
	
	public String getClassificationID() {
		return classificationID;
	}
	public void setClassificationID(String algorithmID) {
		this.classificationID = algorithmID;
	}

	public LinkedList<Implementation> getImplementations() {
		return implementations;
	}

	public void setImplementations(LinkedList<Implementation> implementations) {
		this.implementations = implementations;
	}

	public LinkedList<ProblemInstance> getProblemInstances() {
		return problemInstances;
	}

	public void setProblemInstances(LinkedList<ProblemInstance> problemInstances) {
		this.problemInstances = problemInstances;
	}
	
	

	
	

}
