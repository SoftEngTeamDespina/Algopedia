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
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;
import java.nio.charset.StandardCharsets;

public class CreateProblemInstanceHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testAddInstance(String incoming, String outgoing) throws IOException{
        try {
            CreateProblemInstanceHandler handler  = new CreateProblemInstanceHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("add"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
            assertEquals(outgoing, outputNode.get("errorMessage").asText());
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }

    @Test
    public void testCreateProblemInstance() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();

            Classification testClass = new Classification("test", "desc", null);
            classdb.addClassification(testClass);

            String testID = classdb.getClassification("test").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgo", "desc", testID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgo").getAlgorithmID();

            String testData = "[109,112,103,44,99,121,108,105,110,100,101,114,115,44,100,105,115,112,108,97,99,101,109,101,110,116,44,104]";

            String input = "{\"algoID\": \""+algoID+"\",\"data\":\""+testData+"\",\"desc\": \"desc\",\"userID\": \"testUser\",\"name\":\"name\"}";
            String output = "";


            testAddInstance(input, output);

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