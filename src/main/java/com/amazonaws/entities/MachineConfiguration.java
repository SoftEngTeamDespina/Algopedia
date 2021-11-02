package com.amazonaws.entities;

public class MachineConfiguration {

	private String machineConfigurationID;
	private String cpu;
	private int cores;
	private int threads;
	private String l1;
	private String l2;
	private String l3;
	
	public String getMachineConfigurationID() {
		return machineConfigurationID;
	}
	public void setMachineConfigurationID(String machineConfigurationID) {
		this.machineConfigurationID = machineConfigurationID;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public int getCores() {
		return cores;
	}
	public void setCores(int cores) {
		this.cores = cores;
	}
	public int getThreads() {
		return threads;
	}
	public void setThreads(int threads) {
		this.threads = threads;
	}
	public String getL1() {
		return l1;
	}
	public void setL1(String l1) {
		this.l1 = l1;
	}
	public String getL2() {
		return l2;
	}
	public void setL2(String l2) {
		this.l2 = l2;
	}
	public String getL3() {
		return l3;
	}
	public void setL3(String l3) {
		this.l3 = l3;
	}
}
