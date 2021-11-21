package com.amazonaws.lambda;

import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.UserActionDAO;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.UserAction;
import com.amazonaws.http.CreateImplementationResponse;
import com.amazonaws.regions.Regions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;


public class CreateImplementationHandler implements RequestStreamHandler {
	LambdaLogger logger;
	private AmazonS3 s3 = null;
	public static final String REAL_BUCKET = "implementations/";
	
	
	//RDS
	boolean createImplementation(String language, String filename, String algoID) throws Exception{
		if (logger != null) { logger.log("in createImplementation"); }
		ImplementationDAO dao = new ImplementationDAO();
		
		return dao.addImplementation(new Implementation());
		
		
	}
	
	//S3 bucket
	boolean createSystemImplementation(String filename, byte[] code) throws Exception{
		if (logger != null) { logger.log("in createSystemImplementation"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		
		String bucket = REAL_BUCKET;
		
		ByteArrayInputStream bais = new ByteArrayInputStream(code);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(code.length);
		
		
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("cs509teamdespina", bucket + filename, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		return true;
		
		
	}
	


	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		
		ImplementationDAO iDAO = new ImplementationDAO();
		CreateImplementationResponse response;
		
		UserActionDAO uaDAO =  new UserActionDAO();
		
		if (event.get("language") != null) {
            String language = new Gson().fromJson(event.get("language"), String.class);
            byte[] code = new Gson().fromJson(event.get("code"), byte[].class);
            String algorithmID = new Gson().fromJson(event.get("id"), String.class);
            String fileName = language + algorithmID + ".txt"; // generate file name -> <language><AlgoID>.txt format
            
            String userID = new Gson().fromJson(event.get("user"), String.class);
            
		
		try {

			Implementation newImplementation = new Implementation(language, fileName, algorithmID);
			String implementationID = iDAO.addImplementation(newImplementation);
			if(implementationID == null) {throw new Exception("Failed to insert to table.");}
			logger.log("Storing implementation...");
			response = new CreateImplementationResponse(200, implementationID); 
			
				
			if (!createSystemImplementation(implementationID, code)){throw new Exception("Failed to insert to S3 bucket.");}
			response = new CreateImplementationResponse(200, implementationID);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAction action = new UserAction(userID,"Add Implementation",timestamp.toString());
			uaDAO.addUserAction(action);
			
			writer.write(new Gson().toJson(response));
			
		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new CreateImplementationResponse(500, e.getMessage());
			writer.write(new Gson().toJson(response));
			
		}finally {
			reader.close();
			writer.close();
		}
		
		}
		
		return;
	}

}

