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

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;
import java.nio.charset.StandardCharsets;

public class GetClassificationHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testGetClassificationsAll(String incoming, String outgoing) throws IOException{
        try {
            GetClassificationHandler handler  = new GetClassificationHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("get classification, alorithm, and hierarchy"));
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
    public void testGetClassification() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();

            Classification testClass = new Classification("testClassGC", "desc", null);
            classdb.addClassification(testClass);

            String testClassID = classdb.getClassification("testClassGC").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgoGC", "desc", testClassID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgoGC").getAlgorithmID();
            
            
            String input = "{}";
            String output = "";


            testGetClassificationsAll(input, output);
            
            algodb.removeAlgorithm(algoID);
            classdb.removeClassification(testClassID);  

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