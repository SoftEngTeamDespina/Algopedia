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

public class RemoveAlgorithmHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testRemoveAlgorithm(String incoming, String outgoing) throws IOException{
        try {
            RemoveAlgorithmHandler handler  = new RemoveAlgorithmHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("add"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
            assertEquals(outgoing, outputNode.get("errorMessage").asText());
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " + e);
        }
    }

    @Test
    public void testRemoveAlgorithmReal() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();

            Classification testClass = new Classification("test", "desc", null);
            classdb.addClassification(testClass);

            String testID = classdb.getClassification("test").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgo", "desc", testID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgo").getAlgorithmID();


            String input = "{\"id\": \""+algoID+"\",\"user\": \"testUser\"}";
            String output = "";


            testRemoveAlgorithm(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testRemoveAlgorithmFake() throws Exception{
        try {
            String input = "{\"id\": \"fakeID\",\"user\": \"testUser\"}";
            String output = "Failed to remove algorithm";


            testRemoveAlgorithm(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
}