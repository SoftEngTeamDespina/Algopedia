package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.ProblemInstance;
import com.amazonaws.entities.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class BenchmarkTests {
	
	Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }
	
	 @Test
	    public void testRemoveUser() throws Exception{
	        try {
	            UserDAO userDB = new UserDAO();
	            AlgorithmDAO algodb = new AlgorithmDAO();
	            ClassificationDAO classdb = new ClassificationDAO();
	            ProblemInstanceDAO pidb = new ProblemInstanceDAO();
	            
	            ImplementationDAO imdb = new ImplementationDAO();

	            Classification testClass = new Classification("test1", "desc", null);
	            classdb.addClassification(testClass);
	            String testID = classdb.getClassification("test1").getClassificationID();
	            Algorithm algo =  new Algorithm("testAlgo1", "desc", testID);
	            algodb.addAlgorithm(algo);
	            String algoID = algodb.getAlgorithm("testAlgo1").getAlgorithmID();
	            
	            ProblemInstance pi = new ProblemInstance("test1","description","data",algoID);
	            pidb.addProblemInstance(pi);
	            String piID = pidb.getProblemInstanceNoID(algoID, "test1").getProblemInstanceID();
	            
	            
	            Implementation imp = new Implementation("0","0","0",algoID);
	            imdb.addImplementation(imp);
	            String Impid = imdb.getImplementationByStamp("0").getImplementationID();
	            
	      
	            
	            User u1 = new User("test1","123",false);
	         
	            userDB.addUser(u1);
	            //test get user and addUser Remove User
	           
	            
	            String incoming =  "{\"problem_instance\": \""+piID+"\",\"implementation\":\""+Impid+"\",\"user\": \"test1\",\"runtime\": 3,\"name\":\"test1\",\"cores\":3,\"L3\":\"l3\",\"L2\":\"l2\",\"L1\":\"l1\",\"threads\":2,\"cpu\":\"i7\",\"observation\":\"obs\"}";
	            String outgoing = "";
	            
	        
	            
	            try {
	            CreateBenchmarkHandler handler  = new CreateBenchmarkHandler();
                InputStream input = new ByteArrayInputStream(incoming.getBytes());
                OutputStream output = new ByteArrayOutputStream();
               
                handler.handleRequest(input, output, createContext("add user"));
                JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

                assertEquals(outputNode.get("statusCode").asText(), "200");
            } catch (Exception e) {
                fail("Invalid"+ e);
                System.out.println("error: " +e);
            }
	            
	            userDB.removeUser("test1");
	            pidb.removeProblemInstance(piID);
	            imdb.deleteImplementation(Impid);
	            algodb.removeAlgorithm(algoID);
	            classdb.removeClassification(testID);
	            
	          

	        } catch (Exception e) {
	            fail("Invalid"+ e);
	            System.out.println("error: " +e);
	        }
	    }

    
}
