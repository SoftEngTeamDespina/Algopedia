package com.amazonaws.lambda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Implementation;
import com.amazonaws.http.GetImplementationResponse;
import com.amazonaws.http.GetImplementationsAllResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class GetImplementationHandler implements RequestStreamHandler{
	LambdaLogger logger;

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		
		
		ImplementationDAO dao = new ImplementationDAO();
		GetImplementationResponse response;
		
		if (event.get("id") != null) {
            String implementationID = new Gson().fromJson(event.get("id"), String.class);
            try {
            	logger.log("Getting implementation...");
            	Implementation implementation = dao.getImplementationByID(implementationID);
            	
            	response = new GetImplementationResponse(implementation ,200);
            	writer.write(new Gson().toJson(response));
            	
            } catch(Exception e) {
            	logger.log(e.getMessage());
    			e.printStackTrace();
            	response = new GetImplementationResponse(500, e.getMessage());
            	writer.write(new Gson().toJson(response));
            }finally {
    			reader.close();
    			writer.close();
    		}
            
            
           
            
            
            
            
		
		
		}
		
		
	}

}
