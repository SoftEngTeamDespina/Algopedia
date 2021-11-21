package com.amazonaws.lambda;

import com.amazonaws.db.BenchmarkDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.MachineConfigurationDAO;
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.entities.Benchmark;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.MachineConfiguration;
import com.amazonaws.entities.ProblemInstance;
import com.amazonaws.http.CreateBenchmarkResponse;
import com.amazonaws.http.CreateClassificationResponse;
import com.amazonaws.http.CreateImplementationResponse;
import com.amazonaws.http.GetBenchmarkResponse;
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
import java.util.LinkedList;

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


public class GetAllBenchmarksHandler implements RequestStreamHandler {
	LambdaLogger logger;
	private AmazonS3 s3 = null;
	public static final String REAL_BUCKET = "benchmarks/";
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		logger = context.getLogger();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("US-ASCII")));
		PrintWriter writer = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
		JsonObject event = new GsonBuilder().create().fromJson(reader, JsonObject.class);

		logger.log(event.toString());
		
		BenchmarkDAO db = new BenchmarkDAO();
		GetBenchmarkResponse response;
		
//		private MachineConfiguration configuration;
//		private ProblemInstance instance;
//		private Implementation implementation;
//		private double runtime;
//		private String observations;
//		
		if (event.get("algorithm") != null) {
			
			String name = new Gson().fromJson(event.get("name"), String.class);
		
		try {
			
        	ProblemInstanceDAO pdao = new ProblemInstanceDAO();
        	
        	LinkedList<ProblemInstance>  pi = pdao.getProblemInstanceByAlgorithm(name);
        	System.out.println(pi.getFirst());
        	LinkedList<Benchmark> ret = new LinkedList<Benchmark>();
        	
        	for(ProblemInstance p: pi) {
        		Benchmark temp = db.getBenchmarkByProblemInstance(p.getProblemInstanceID());
        		ret.add(temp);
        	}
        	
        	response = new GetBenchmarkResponse(ret,200);
			writer.write(new Gson().toJson(response));

		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new GetBenchmarkResponse(500, "Failed to get benchmark");
			writer.write(new Gson().toJson(response));

		}finally {
			reader.close();
			writer.close();
		}

		}

		return;
	}
}

