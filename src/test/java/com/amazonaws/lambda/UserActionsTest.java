package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

import org.junit.Test;

import com.amazonaws.db.UserActionDAO;
import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.User;
import com.amazonaws.entities.UserAction;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class UserActionsTest {
	Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

	   @Test
	    public void GetUserActionsForUserWrong() throws Exception{
	          
	    	String incoming = "{username:'-'}";
	        String outgoing = "";
    		
	        try {
	        	
	            GetUserActions handler  = new GetUserActions();
	            InputStream input = new ByteArrayInputStream(incoming.getBytes());
	            OutputStream output = new ByteArrayOutputStream();
	            handler.handleRequest(input, output, createContext("get users"));
	            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
	            
	            assertEquals(outputNode.get("logMsg").asText(), "Failed to Get user action data");
	        } catch (Exception e) {
	            fail("Invalid"+ e);
	            System.out.println("error: " +e);
	        }
	          
	    }
	   //Failed to Get user action data
	   @Test
	    public void GetUserActionsForUser() throws Exception{
	          
	    	String incoming = "{username:'test1'}";
	        String outgoing = "";
	        UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            UserActionDAO uaDAO =  new UserActionDAO();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			UserAction action = new UserAction("test1","Add Classification",timestamp.toString());
			action.getTimeStamp();
			action.setTimeStamp(timestamp.toString());
			action.getAction();
			action.getUserActionID();
			action.setAction("add classification");
			
	        try {
	        	userDB.addUser(u1);
	        	uaDAO.addUserAction(action);
	            GetUserActions handler  = new GetUserActions();
	            InputStream input = new ByteArrayInputStream(incoming.getBytes());
	            OutputStream output = new ByteArrayOutputStream();
	            handler.handleRequest(input, output, createContext("get users"));
	            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
	            userDB.removeUser("test1");
	            assertEquals(outputNode.get("logMsg").asText(), "List Of Users actions for user");
	        } catch (Exception e) {
	            fail("Invalid"+ e);
	            System.out.println("error: " +e);
	        }
	          
	    }
	   
	}
    
