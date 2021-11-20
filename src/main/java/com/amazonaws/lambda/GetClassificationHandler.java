package com.amazonaws.lambda;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.http.CreateClassificationResponse;
import com.amazonaws.http.GetClassificationResponse;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;


public class GetClassificationHandler implements RequestStreamHandler {
	LambdaLogger logger;



	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		ClassificationDAO db = new ClassificationDAO();
		AlgorithmDAO adb = new AlgorithmDAO();
		ImplementationDAO impdb = new ImplementationDAO();
		GetClassificationResponse response;
		logger.log(event.toString());
		
		try {
			
			LinkedList<Classification> ClassificationsList = db.getAllClassifications();
			
			if (ClassificationsList.size() > 0){
				logger.log("Fetching all algorithms for all classifications");
				for(Classification c: ClassificationsList) {
					LinkedList<Algorithm>  algorithms = adb.getAlgorithms(c.getClassificationID());
					for(Algorithm a: algorithms) {
						LinkedList<Implementation> imps = impdb.getAllImplementations(a.getAlgorithmID());
						a.setImplementations(imps);
					}
					c.setAlgorithms(algorithms);
				}
				response = new GetClassificationResponse(200,ClassificationsList);
			}
			else {
				response = new GetClassificationResponse(400, "There are no Classifications");
			}
			writer.write(new Gson().toJson(response));

		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new GetClassificationResponse(500, "Failed to find classifications");
			writer.write(new Gson().toJson(response));

		}finally {
			reader.close();
			writer.close();
		}

		

		return;
	}

}

