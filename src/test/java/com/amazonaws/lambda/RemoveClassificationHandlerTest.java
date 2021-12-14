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

public class RemoveClassificationHandlerTest {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testRemoveAlgorithm(String incoming, String outgoing) throws IOException{
        try {
            RemoveClassificationHandler handler  = new RemoveClassificationHandler();
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
    public void testRemoveClassificationReal() throws Exception{
        try {
            ClassificationDAO classdb = new ClassificationDAO();

            Classification testClass = new Classification("test", "desc", null);
            classdb.addClassification(testClass);

            String testID = classdb.getClassification("test").getClassificationID();

            String input = "{\"id\": \""+testID+"\",\"user\": \"testUser\"}";
            String output = "";


            testRemoveAlgorithm(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testRemoveClassificationFake() throws Exception{
        try {
            String input = "{\"id\": \"fakeID\",\"user\": \"testUser\"}";
            String output = "Failed to remove classification";


            testRemoveAlgorithm(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
}