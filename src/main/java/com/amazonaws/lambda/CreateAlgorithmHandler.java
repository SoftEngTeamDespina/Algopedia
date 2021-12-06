package com.amazonaws.lambda;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.UserActionDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.UserAction;
import com.amazonaws.http.CreateAlgorithmResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;


public class CreateAlgorithmHandler implements RequestStreamHandler {
	LambdaLogger logger;
	


	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		CreateAlgorithmResponse response;
		
		AlgorithmDAO db = new AlgorithmDAO();
		
		if (event.get("name") != null) {
			String username = new Gson().fromJson(event.get("user"), String.class);
            String name = new Gson().fromJson(event.get("name"), String.class);
            String description = new Gson().fromJson(event.get("description"), String.class);
            String classificationID = new Gson().fromJson(event.get("id"), String.class);
		
		try {
			
			Algorithm newAlgo = new Algorithm(name, description, classificationID);
			if (db.addAlgorithm(newAlgo)) {
				String algoID = db.getAlgorithm(name).getAlgorithmID();
				response = new CreateAlgorithmResponse(algoID, 200);
				
				UserActionDAO uaDAO =  new UserActionDAO();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				UserAction action = new UserAction(username,"Add Algorithm",timestamp.toString());
				uaDAO.addUserAction(action);
				
			}
			else {
				response = new CreateAlgorithmResponse(400, "Failed to create algorithm");
			}
			
			
			writer.write(new Gson().toJson(response));
			
		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new CreateAlgorithmResponse(500, "Failed to create algorithm");
			writer.write(new Gson().toJson(response));
			
		}finally {
			reader.close();
			writer.close();
		}
		
		}
		
		return;
	}

}

