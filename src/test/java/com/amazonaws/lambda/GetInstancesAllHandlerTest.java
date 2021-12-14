package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.ProblemInstance;
import com.amazonaws.http.GetImplementationsAllResponse;
import com.amazonaws.http.GetProblemInstancesAllResponse;
import com.amazonaws.http.MergeClassificationResponse;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;
import java.nio.charset.StandardCharsets;

public class GetInstancesAllHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testGetInstancesAll(String incoming, String outgoing) throws IOException{
        try {
            GetProblemInstancesAllHandler handler  = new GetProblemInstancesAllHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("get all problem instances"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
            String outputString = outputNode.get("errorMessage").asText();
            if (!outputString.equals("")) {
            	outputString =  outputString.substring(0,6);
            }
            assertEquals(outgoing, outputString);
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }

    @Test
    public void testGetInstances() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();
            ProblemInstanceDAO instdb = new ProblemInstanceDAO();

            Classification testClass = new Classification("testClassGI", "desc", null);
            classdb.addClassification(testClass);

            String testClassID = classdb.getClassification("testClassGI").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgoGI", "desc", testClassID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgoGI").getAlgorithmID();
            
            ProblemInstance inst = new ProblemInstance("testInstGI","testDescription","testDataset",algoID);
            instdb.addProblemInstance(inst);
            
            String input = "{\"algorithm\": \"testAlgoGI\"}";
            String output = "";


            testGetInstancesAll(input, output);
            
            algodb.removeAlgorithm(algoID);
            classdb.removeClassification(testClassID);  
            
            GetProblemInstancesAllResponse response = new GetProblemInstancesAllResponse(new LinkedList<ProblemInstance>(),200);
            response.toString();
            
            response = new GetProblemInstancesAllResponse(400,"Failed");
            response.toString();


        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testBadGetInstances() throws Exception{
        try {

            String algorithm = "Algorithm that does not exist";

            String input = "{\"algorithm\": \""+algorithm+"\"}";
            String output = "Failed";


            testGetInstancesAll(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    public static byte[] fromHexString(String src) {
        byte[] biBytes = new BigInteger("10" + src.replaceAll("\\s", ""), 16).toByteArray();
        return Arrays.copyOfRange(biBytes, 1, biBytes.length);
    }
}