package com.amazonaws.entities;

import java.util.LinkedList;

public class Classification {
	private String classificationID;
	private String name;
	private String description;
	private Classification superClassification;
	private LinkedList<Algorithm> algorithms;
	
	public String getClassificationID() {
		return classificationID;
	}
	public void setClassificationID(String classificationID) {
		this.classificationID = classificationID;
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
	public Classification getSuperClassification() {
		return superClassification;
	}
	public void setSuperClassification(Classification superClassification) {
		this.superClassification = superClassification;
	}
	public LinkedList<Algorithm> getAlgorithms() {
		return algorithms;
	}
	public void setAlgorithms(LinkedList<Algorithm> algorithms) {
		this.algorithms = algorithms;
	}
	
	
}
