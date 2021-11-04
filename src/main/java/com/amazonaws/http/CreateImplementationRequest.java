package com.amazonaws.http;

import java.io.File;

import com.amazonaws.entities.Language;

public class CreateImplementationRequest {
	String implementationID;
	Language language;
	File code;
	String algorithmID;
	
	public String getImplementationID() {return this.implementationID;}
	public void setImplementationID(String implementationID) {this.implementationID = implementationID;}
	
	public Language getLanguage() {return this.language;}
	public void setLanguage(Language language) {this.language = language;}
	
	public File getCode() {return this.code;}
	public void setCode(File code) {this.code = code;}
	
	public String getAlgorithmID() {return this.algorithmID;}
	public void setAlgorithmID(String algorithmID) {this.algorithmID = algorithmID;}
	
	public String toString() {
		return "Create Implementation: " + implementationID + ", " + language;
	}
	
	
	public CreateImplementationRequest(String implementationID, Language language, File code, String algorithmID){
		this.implementationID = implementationID;
		this.language = language;
		this.code = code;
		this.algorithmID = algorithmID;
	}
	
	public CreateImplementationRequest() {
		
	}
	
	

}
