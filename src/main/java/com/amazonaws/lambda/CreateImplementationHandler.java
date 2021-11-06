package com.amazonaws.lambda;

import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.Language;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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
	boolean createImplementation(Language language, String filename, String algoID) throws Exception{
		if (logger != null) { logger.log("in createImplementation"); }
		ImplementationDAO dao = new ImplementationDAO();
		
		return dao.addImplementation(new Implementation());
		
		
	}
	
	//S3 bucket
	boolean createSystemImplementation(String UID, String code) throws Exception{
		if (logger != null) { logger.log("in createSystemImplementation"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		
		String bucket = REAL_BUCKET;
		
		byte[] file = new BigInteger(code, 2).toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(file);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(file.length);
		
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("cs509teamdespina", bucket + UID, bais, omd)
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
		
		ImplementationDAO db = new ImplementationDAO();
		CreateImplementationResponse response;
		
		if (event.get("language") != null) {
            String language = new Gson().fromJson(event.get("language"), String.class);
            String file = new Gson().fromJson(event.get("code"), String.class);
            String algorithmID = new Gson().fromJson(event.get("id"), String.class);
            

		
		try {
			//Add Classification constructor
			Implementation newImp = new Implementation();
			String impID = db.getImplementation(language).getImplementationID();
			
			//to fix
			if (createImplementation(newImp.getLanguage(),newImp.getImplementationID(),newImp.getImplementationID())) {
				response = new CreateImplementationResponse(impID, 200));
			}
			else {
				response = new CreateImplementationResponse(400, "Failed to create implementation RDS");
			}
			if (createSystemImplementation(newImp.getImplementationID(), file)){
				response = new CreateImplementationResponse(impID, 200);
			}
			else {
				response = new CreateImplementationResponse(400, "Failed to create implementation S3 bucket");
			}
			writer.write(new Gson().toJson(response));
			
		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new CreateImplementationResponse(500, "Failed to create implementation");
			writer.write(new Gson().toJson(response));
			
		}finally {
			reader.close();
			writer.close();
		}
		
		}
		
		return;
	}

}

