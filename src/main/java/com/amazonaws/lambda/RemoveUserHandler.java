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
import com.amazonaws.http.LoginResponse;
import com.amazonaws.http.RegisterUserResponse;
import com.amazonaws.http.RemoveUserResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

//aws:states:opt-out	

public class RemoveUserHandler implements RequestStreamHandler {

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
		RemoveUserResponse response = new RemoveUserResponse();
		
		logger.log(event.toString());
		if (event.get("username") != null) {
            String username = new Gson().fromJson(event.get("username"), String.class);
           
            try {
            	
            	if(userDao.removeUser(username)) {
            		response.setUsername(username);
            		response.setHttpStatusCode(200);
            		response.setLogMsg("remove user with username");
            	}
     
            	writer.write(new Gson().toJson(response));
    
            	
			} catch (Exception e) {
				logger.log(e.getMessage());
				e.printStackTrace();
				response.setLogMsg("Failed to remove user");
				response.setHttpStatusCode(500);
				writer.write(new Gson().toJson(response));
			} finally {
				reader.close();
				writer.close();
			}
		}
		
		return;
	}
	

}
