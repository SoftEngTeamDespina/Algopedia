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
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class DownloadImplementationHandlerTest {
	Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
	
	void testDownloadImp(String incoming, String outgoing) throws IOException{
        try {
            RemoveImplementationHandler handler  = new RemoveImplementationHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("add"));
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
    public void testDownloadImplementation() throws Exception{
        try {


            String input = "{\"user\": \"testUser\"}";
            String output = "";
            

            testDownloadImp(input, output);
             

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }


    @Test
    public void testBadDownloadImplementation() throws Exception{
        try {


            String input = "{\"user\": \"does not exist\"}";
            String output = "Failed";


            testDownloadImp(input, output);

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
