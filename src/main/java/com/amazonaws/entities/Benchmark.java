package com.amazonaws.entities;

import java.sql.Date;

public class Benchmark {
	
	private String benchmarkID;
	private Date date;
	private String name;
	private MachineConfiguration configuration;
	private ProblemInstance instance;
	private Implementation implementation;
	private double runtime;
	private String observations;
	
	
	public Benchmark(String benchmarkID, Date date, String name,Implementation implementation ,MachineConfiguration configuration, ProblemInstance instance,
			double runtime, String observations) {
		this.benchmarkID = benchmarkID;
		this.date = date;
		this.name = name;
		this.implementation = implementation;
		this.configuration = configuration;
		this.instance = instance;
		this.runtime = runtime;
		this.observations = observations;
	}
	
	public Benchmark(String name, Implementation implementation ,MachineConfiguration configuration, ProblemInstance instance,
			double runtime, String observations) {
		this.name = name;
		this.implementation = implementation;
		this.configuration = configuration;
		this.instance = instance;
		this.runtime = runtime;
		this.observations = observations;
	}
	
	
	
	public Implementation getImplementation() {
		return implementation;
	}
	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}
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
