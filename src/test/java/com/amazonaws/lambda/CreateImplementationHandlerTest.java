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
import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.User;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;
import java.nio.charset.StandardCharsets;

public class CreateImplementationHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testAddImplementation(String incoming, String outgoing) throws IOException{
        try {
            CreateImplementationHandler handler  = new CreateImplementationHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("create implementation"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
            String outputString = outputNode.get("errorMessage").asText();
            if (!outputString.equals("")) {
            	outputString =  outputString.substring(0,5);
            }
            assertEquals(outgoing, outputString);
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }

    @Test
    public void testCreateImplementation() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();
            UserDAO userdb = new UserDAO();

            Classification testClass = new Classification("testClassCI", "desc", null);
            classdb.addClassification(testClass);

            String testClassID = classdb.getClassification("testClassCI").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgoCI", "desc", testClassID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgoCI").getAlgorithmID();
            
            String username = "testUser";
            User user = new User(username, "pass", false);
            userdb.addUser(user);

            String testData = "[109,112,103,44,99,121,108,105,110,100,101,114,115,44,100,105,115,112,108,97,99,101,109,101,110,116,44]";

            String input = "{\"algorithm\": \""+algoID+"\",\"code\":\""+testData+"\",\"language\": \"Java\",\"user\": \""+username+"\"}";
            String output = "";


            testAddImplementation(input, output);
            
            userdb.removeUser(username);
            algodb.removeAlgorithm(algoID);
            classdb.removeClassification(testClassID);  

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testBadImplementation() throws Exception{
        try {

            String testData = "[109,112,103,44,99,121,108,105,110,100,101,114,115,44,100,105,115,112,108,97,99,101,109,101,110,116,44]";

            String input = "{\"algoID\": \" \",\"code\":\""+testData+"\",\"language\": \"Java\",\"user\": \"testUser\"}";
            String output = "Failed";


            testAddImplementation(input, output);

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