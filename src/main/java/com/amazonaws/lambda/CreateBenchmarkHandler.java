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


public class CreateBenchmarkHandler implements RequestStreamHandler {
	LambdaLogger logger;
	private AmazonS3 s3 = null;
	public static final String REAL_BUCKET = "benchmarks/";
	
	
	//RDS
	boolean createBenchmark( Benchmark bench ) throws Exception{
		if (logger != null) { logger.log("in benchmark"); }
		BenchmarkDAO dao = new BenchmarkDAO();
		return dao.addBenchmark(bench);
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
		
//		byte[] file = new code.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(code);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(code.length);
		
		
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("cs509teamdespina", filename, bais, omd)
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
		
		BenchmarkDAO db = new BenchmarkDAO();
		CreateBenchmarkResponse response;
		
//		private MachineConfiguration configuration;
//		private ProblemInstance instance;
//		private Implementation implementation;
//		private double runtime;
//		private String observations;
//		
		if (event.get("name") != null) {
			
			String name = new Gson().fromJson(event.get("name"), String.class);
            //String MachineConfig = new Gson().fromJson(event.get("machine_config"), String.class);
            String ProblemInstance = new Gson().fromJson(event.get("problem_instance"), String.class);
            String Implementation = new Gson().fromJson(event.get("implementation"), String.class);
            double runtime =  new Gson().fromJson(event.get("runtime"), double.class);
            String observations = new Gson().fromJson(event.get("observations"), String.class);
            String cpu = new Gson().fromJson(event.get("cpu"), String.class);;
            int cores  =  new Gson().fromJson(event.get("cores"), int.class);;
            int threads  =  new Gson().fromJson(event.get("threads"), int.class);;
            String L1 = new Gson().fromJson(event.get("l1"), String.class);;
            String L2 = new Gson().fromJson(event.get("l2"), String.class);;
            String L3 = new Gson().fromJson(event.get("l3"), String.class);;


		try {
			ImplementationDAO idao = new ImplementationDAO();
        	MachineConfigurationDAO mdao = new MachineConfigurationDAO();
        	ProblemInstanceDAO pdao = new ProblemInstanceDAO();
        	
        	long now = System.currentTimeMillis();
        	Timestamp sqlTimestamp = new Timestamp(now);
        	String ID =  "";
        	MachineConfiguration mconfig  = new MachineConfiguration(sqlTimestamp.toString(),cpu,cores,threads,L1,L2,L3);
        	MachineConfiguration mconf;
        	if(mdao.addMachineConfig(mconfig)){
        		mconf = mdao.getMachineConfigByStamp(sqlTimestamp);
        		if(mconf == null) {
        			throw new Exception("Failed to create machine confing because is null" );
        		}
        	}
        	else {
        		throw new Exception("Failed to create machine confing");
        	}
        	System.out.println(sqlTimestamp);
        		 
			Benchmark newBench = new Benchmark(name,idao.getImplementationByID(Implementation),mconf,pdao.getProblemInstance(ProblemInstance),runtime,observations);
			if (db.addBenchmark(newBench)){
				logger.log("inserting the new benchmark");
				Benchmark b = db.getBenchmark(name);
				response = new CreateBenchmarkResponse(b.getBenchmarkID(),200);
			}
			else {
				response = new CreateBenchmarkResponse(400, "Failed to create benchmark");
			}
			writer.write(new Gson().toJson(response));

		} catch (Exception e){
			logger.log(e.getMessage());
			e.printStackTrace();
			response = new CreateBenchmarkResponse(500, "Failed to create benchmark");
			writer.write(new Gson().toJson(response));

		}finally {
			reader.close();
			writer.close();
		}

		}

		return;
	}
}

