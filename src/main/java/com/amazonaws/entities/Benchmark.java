package com.amazonaws.entities;

import java.util.Date;

public class Benchmark {
	private String benchmarkID;
	private Date date;
	private MachineConfiguration configuration;
	private ProblemInstance instance;
	private double runtime;
	private String observations;
	
	
	public String getBenchmarkID() {
		return benchmarkID;
	}
	public void setBenchmarkID(String benchmarkID) {
		this.benchmarkID = benchmarkID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public MachineConfiguration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(MachineConfiguration configuration) {
		this.configuration = configuration;
	}
	public ProblemInstance getInstance() {
		return instance;
	}
	public void setInstance(ProblemInstance instance) {
		this.instance = instance;
	}
	public double getRuntime() {
		return runtime;
	}
	public void setRuntime(double runtime) {
		this.runtime = runtime;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	
	
}
