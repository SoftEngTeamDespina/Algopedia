package com.amazonaws.lambda;

import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.db.UserActionDAO;
import com.amazonaws.entities.ProblemInstance;
import com.amazonaws.entities.UserAction;
import com.amazonaws.http.CreateProblemInstanceResponse;
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


public class CreateProblemInstanceHandler implements RequestStreamHandler {
	LambdaLogger logger;
	private AmazonS3 s3 = null;
	public static final String REAL_BUCKET = "ProblemInstances/";
	
	
	
	//S3 bucket
	boolean createSystemInstance(String filename, byte[] code) throws Exception{
		if (logger != null) { logger.log("in createSystemInstance"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		
		String bucket = REAL_BUCKET;
		
		ByteArrayInputStream bais = new ByteArrayInputStream(code);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(code.length);
		
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("cs509teamdespina", bucket + filename, bais, omd)
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
		CreateProblemInstanceResponse response;
		
		ProblemInstanceDAO db = new ProblemInstanceDAO();
        UserActionDAO uaDAO =  new UserActionDAO();
		
		if (event.get("name") != null) {
            String name = new Gson().fromJson(event.get("name"), String.class);
            String desc = new Gson().fromJson(event.get("desc"), String.class);
            String rawCode = new Gson().fromJson(event.get("data"), String.class); 
            String userID = new Gson().fromJson(event.get("user"), String.class);
            String algoID = new Gson().fromJson(event.get("algoID"), String.class);
            String file = name+algoID+".txt";

            String[] byteValues = rawCode.substring(1, rawCode.length() - 1).split(",");
            byte[] bytes = new byte[byteValues.length];
            for (int i=0, len=bytes.length; i<len; i++) {
               bytes[i] = Byte.parseByte(byteValues[i].trim());     
            }

            ProblemInstance temp = new ProblemInstance(name, desc, file, algoID);
            String instID = "tempID";
            try {
                if (db.addProblemInstance(temp)) {
                    instID = db.getProblemInstanceNoID(algoID, name).getProblemInstanceID();

                    response = new CreateProblemInstanceResponse(file,200,""); 
			
				
                    if (!createSystemInstance(file, bytes)){

                        throw new Exception("Failed to insert to S3 bucket.");
                    }
                    response = new CreateProblemInstanceResponse(instID, 200,"");

                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    UserAction action = new UserAction(userID,"Created Problem Instance",timestamp.toString());
                    uaDAO.addUserAction(action);
                }
                else {
                    response = new CreateProblemInstanceResponse(instID,400, "Failed to create Problem Instance");
                }
                writer.write(new Gson().toJson(response));
            } catch (Exception e){
                logger.log(e.getMessage());
                e.printStackTrace();
                response = new CreateProblemInstanceResponse(instID,500, "Failed to create Problem Instance");
                writer.write(new Gson().toJson(response));
            }finally {
                reader.close();
                writer.close();
            }
		}
		return;
	}
}
