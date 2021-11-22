package com.amazonaws.lambda;

import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.UserActionDAO;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.UserAction;
import com.amazonaws.http.CreateImplementationResponse;
import com.amazonaws.http.RemoveImplementationResponse;
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


public class RemoveImplementationHandler implements RequestStreamHandler {
	LambdaLogger logger;
	private AmazonS3 s3 = null;
	public static final String REAL_BUCKET = "implementations/";
	
	
	

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		
		ImplementationDAO iDAO = new ImplementationDAO();
		RemoveImplementationResponse response;
		
		UserActionDAO uaDAO =  new UserActionDAO();
		
		if (event.get("id") != null) {
            String implementationID = new Gson().fromJson(event.get("id"), String.class);
            
            String userID = new Gson().fromJson(event.get("user"), String.class);
            
		
		try {
			if(!iDAO.deleteImplementation(implementationID)) {throw new Exception("Failed to delete implementation");}
			logger.log("Deleting implementation...");
			
			response = new RemoveImplementationResponse(implementationID, 200); 
			
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAction action = new UserAction(userID,"Remove Implementation",timestamp.toString());
			uaDAO.addUserAction(action);
			
			writer.write(new Gson().toJson(response));
			
		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new RemoveImplementationResponse(500, e.getMessage());
			writer.write(new Gson().toJson(response));
			
		}finally {
			reader.close();
			writer.close();
		}
		
		}
		
		return;
	}

}

