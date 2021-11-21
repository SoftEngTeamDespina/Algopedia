package com.amazonaws.lambda;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.UserActionDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.UserAction;
import com.amazonaws.http.RemoveAlgorithmResponse;

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


public class RemoveAlgorithmHandler implements RequestStreamHandler {
	LambdaLogger logger;
	


	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		RemoveAlgorithmResponse response;
		
		AlgorithmDAO db = new AlgorithmDAO();
        UserActionDAO uaDAO =  new UserActionDAO();
		
		if (event.get("id") != null) {
            String algoID = new Gson().fromJson(event.get("id"), String.class);
            String userID = new Gson().fromJson(event.get("user"), String.class);
		
            try {
                if (db.removeAlgorithm(algoID)) {
                    response = new RemoveAlgorithmResponse(200,"");
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    UserAction action = new UserAction(userID,"Remove Algorithm: " + algoID,timestamp.toString());
                    uaDAO.addUserAction(action);
                }
                else {
                    response = new RemoveAlgorithmResponse(400, "Failed to remove algorithm");
                }
                writer.write(new Gson().toJson(response));
            } catch (Exception e){
                logger.log(e.getMessage());
                e.printStackTrace();
                response = new RemoveAlgorithmResponse(500, "Failed to remove algorithm");
                writer.write(new Gson().toJson(response));
            }finally {
                reader.close();
                writer.close();
            }
		}
		return;
	}
}