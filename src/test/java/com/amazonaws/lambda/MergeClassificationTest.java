package com.amazonaws.lambda;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.http.MergeClassificationResponse;
import com.amazonaws.http.RemoveAlgorithmResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class MergeClassificationTest {

    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testMerge(String incoming, String outgoing) throws IOException{
        try {
            MergeClassificationHandler handler  = new MergeClassificationHandler();
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
	public void testEmpty() throws Exception {
        try {
            ClassificationDAO classdb = new ClassificationDAO();
            Classification keepClass = new Classification("keep", "desc", null);
            Classification mergeClass = new Classification("merge", "desc", null);

            classdb.addClassification(keepClass);
            classdb.addClassification(mergeClass);

            String keepID = classdb.getClassification("keep").getClassificationID();
            String mergeID = classdb.getClassification("merge").getClassificationID();

            String input = "{\"keepID\": \""+keepID+"\",\"mergeID\":\""+mergeID+"\",\"user\": \"testUser\"}";
            String output = "";


            testMerge(input, output);
            
            MergeClassificationResponse response = new MergeClassificationResponse(200,"");
            response.toString();
            
            response = new MergeClassificationResponse(400, "Failed");
            response.toString();

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
	}

    @Test
	public void testChild() throws Exception {
        try {
            ClassificationDAO classdb = new ClassificationDAO();
            Classification keepClass = new Classification("keep", "desc", null);
            Classification mergeClass = new Classification("merge", "desc", null);

            classdb.addClassification(keepClass);
            classdb.addClassification(mergeClass);

            String keepID = classdb.getClassification("keep").getClassificationID();
            String mergeID = classdb.getClassification("merge").getClassificationID();

            Classification childClass = new Classification("child", "desc", mergeID);
            classdb.addClassification(childClass);


            String input = "{\"keepID\": \""+keepID+"\",\"mergeID\":\""+mergeID+"\",\"user\": \"testUser\"}";
            String output = "";


            testMerge(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
	}

    @Test
	public void testWithAlgo() {
        try {
            ClassificationDAO classdb = new ClassificationDAO();
            Classification keepClass = new Classification("keep", "desc", null);
            Classification mergeClass = new Classification("merge", "desc", null);

            classdb.addClassification(keepClass);
            classdb.addClassification(mergeClass);

            String keepID = classdb.getClassification("keep").getClassificationID();
            String mergeID = classdb.getClassification("merge").getClassificationID();

            AlgorithmDAO algobd = new AlgorithmDAO();
            Algorithm algo = new Algorithm("test", "description", mergeID);
            algobd.addAlgorithm(algo);

            String input = "{\"keepID\": \""+keepID+"\",\"mergeID\":\""+mergeID+"\",\"user\": \"testUser\"}";
            String output = "";


            testMerge(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
	}
	
	@Test
	public void testMissingClass() throws Exception {
        try {
            ClassificationDAO classdb = new ClassificationDAO();
            Classification keepClass = new Classification("keep", "desc", null);

            classdb.addClassification(keepClass);

            String keepID = classdb.getClassification("keep").getClassificationID();

            String input = "{\"keepID\": \""+keepID+"\",\"mergeID\":\" \",\"user\": \"testUser\"}";
            String output = "Failed to merge classification";


            testMerge(input, output);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
	}
}
