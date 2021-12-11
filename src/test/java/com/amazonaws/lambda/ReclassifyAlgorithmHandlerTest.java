package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;

public class ReclassifyAlgorithmHandlerTest {
    
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testReclassify(String incoming, String outgoing) throws IOException{
        try {
            ReclassifyAlgorithmHandler handler  = new ReclassifyAlgorithmHandler();
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
    public void testReclassifyEmptyAlgo() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();

            Classification oldClass = new Classification("old", "desc", null);
            Classification newClass = new Classification("new", "desc", null);
            classdb.addClassification(oldClass);
            classdb.addClassification(newClass);

            String oldID = classdb.getClassification("old").getClassificationID();
            String newID = classdb.getClassification("new").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgo", "desc", oldID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgo").getAlgorithmID();

            String input = "{\"algoID\": \""+algoID+"\",\"classID\": \""+newID+"\",\"user\": \"testUser\"}";
            String output = "";


            testReclassify(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testReclassifyfakeAlgo() throws Exception{
        try {
            ClassificationDAO classdb = new ClassificationDAO();

            Classification newClass = new Classification("new", "desc", null);
            classdb.addClassification(newClass);

            String newID = classdb.getClassification("new").getClassificationID();

            String input = "{\"algoID\": \" \",\"classID\": \""+newID+"\",\"user\": \"testUser\"}";
            String output = "Failed to Reclassify Algorithm";


            testReclassify(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }

    @Test
    public void testReclassifyMissingClass() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();

            Classification oldClass = new Classification("old", "desc", null);
            Classification newClass = new Classification("new", "desc", null);
            classdb.addClassification(oldClass);
            classdb.addClassification(newClass);

            String oldID = classdb.getClassification("old").getClassificationID();

            Algorithm algo =  new Algorithm("testAlgo", "desc", oldID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlgo").getAlgorithmID();

            String input = "{\"algoID\": \""+algoID+"\",\"classID\": \" \",\"user\": \"testUser\"}";
            String output = "Failed to Reclassify Algorithm";


            testReclassify(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
}
