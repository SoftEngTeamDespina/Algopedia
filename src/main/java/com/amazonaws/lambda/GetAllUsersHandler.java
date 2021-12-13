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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.User;
import com.amazonaws.http.GetAllUsersResponse;
import com.amazonaws.http.LoginResponse;
import com.amazonaws.http.RegisterUserResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

//aws:states:opt-out	

public class GetAllUsersHandler implements RequestStreamHandler {

	LambdaLogger logger;
	
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		// TODO Auto-generated method stub
		logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);
		UserDAO userDao = new UserDAO();
		GetAllUsersResponse response = new GetAllUsersResponse();
		response.setLogMsg("Failed to Get user data");
		response.setHttpStatusCode(500);
		
		logger.log(event.toString());
            try {
            	response.setHttpStatusCode(200);
        		response.setLogMsg("List Of Users");
        		response.setUsername(userDao.getAllUsers());
        		writer.write(new Gson().toJson(response));
    
            	
			} catch (Exception e) {
				e.printStackTrace();
				writer.write(new Gson().toJson(response));
			} finally {
				reader.close();
				writer.close();
			}
		
		
		return;
	}
	

}
