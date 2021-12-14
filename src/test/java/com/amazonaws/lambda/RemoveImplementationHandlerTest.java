package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.User;
import com.amazonaws.http.MergeClassificationResponse;
import com.amazonaws.http.RemoveImplementationResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class RemoveImplementationHandlerTest {
	Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
	
	void testDeleteImplementation(String incoming, String outgoing) throws IOException{
        try {
            RemoveImplementationHandler handler  = new RemoveImplementationHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("remove implementation"));
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
    public void testRemoveImplementation() throws Exception{
        try {
            AlgorithmDAO algodb = new AlgorithmDAO();
            ClassificationDAO classdb = new ClassificationDAO();
            ImplementationDAO impdb = new ImplementationDAO();
            UserDAO userdb = new UserDAO();
            
            String className = "testRemoveImplementation";
            Classification testClass = new Classification(className, "desc", null);
            classdb.addClassification(testClass);

            String testClassID = classdb.getClassification(className).getClassificationID();

            Algorithm algo =  new Algorithm("testAlg", "desc", testClassID);
            algodb.addAlgorithm(algo);
            String algoID = algodb.getAlgorithm("testAlg").getAlgorithmID();
            
            String testTimeStamp = "testTimeStamp";
            Implementation imp = new Implementation(testTimeStamp,"testLanguage","testFilename",algoID);
            impdb.addImplementation(imp);
            String impID = impdb.getImplementationByStamp(testTimeStamp).getImplementationID();
            
            String username = "testUser";
            User user = new User(username, "pass", false);
            userdb.addUser(user);

            String input = "{\"id\": \""+impID+"\",\"user\": \""+username+"\"}";
            String output = "";
            

            testDeleteImplementation(input, output);
            
            userdb.removeUser(username);
            algodb.removeAlgorithm(algoID);
            classdb.removeClassification(testClassID);  
            
            RemoveImplementationResponse response = new RemoveImplementationResponse("id",200);
            response.toString();
            
            response = new RemoveImplementationResponse(400, "Failed");
            response.toString();

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testBadDeletedImplementation() throws Exception{
        try {

            String impID = "Not Real ID";

            String input = "{\"id\": \""+impID+"\",\"user\": \"testUser\"}";
            String output = "Failed";


            testDeleteImplementation(input, output);

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
