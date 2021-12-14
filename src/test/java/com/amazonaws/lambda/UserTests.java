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
import com.amazonaws.http.MergeClassificationResponse;
import com.amazonaws.http.RegisterUserResponse;
import com.amazonaws.http.RemoveUserResponse;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

import org.junit.Test;

import org.junit.Assert;
import java.nio.charset.StandardCharsets;

public class UserTests {
    Context createContext(String apiCall){
        TestContext ctx = new TestContext();
        ctx.setFunctionName(apiCall);
        return ctx;
    }

    void testAddUser(String incoming, String outgoing) throws IOException{
        try {
            HandleRegisterUser handler  = new HandleRegisterUser();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
           
            handler.handleRequest(input, output, createContext("add user"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

            assertEquals(outputNode.get("logMsg").asText(), "Registration succesful");
            
            RegisterUserResponse response = new RegisterUserResponse(new User("test","pass",false),200,"");
            response.toString();
            
            response = new RegisterUserResponse(new User("test","pass",false),400, "Failed");
            response.toString();

            
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    
    
    void testRemoveUser(String incoming, String outgoing) throws IOException{
        try {
            RemoveUserHandler handler  = new RemoveUserHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
           
            handler.handleRequest(input, output, createContext("add user"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

            assertEquals(outputNode.get("logMsg").asText(), "remove user with username");
            
            RemoveUserResponse response = new RemoveUserResponse("user",200,"");
            response.toString();
            
            response = new RemoveUserResponse("user",400, "Failed");
            response.toString();
            
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    void Login(String incoming, String outgoing) throws IOException{
        try {
            HandleLogin handler  = new HandleLogin();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
           
            handler.handleRequest(input, output, createContext("add user"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

            assertEquals(outputNode.get("logMsg").asText(), "User authenticated");
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    @Test
    public void testCreateUser() throws Exception{
        try {
            UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            User u2 = new User("test1","123");
            
            //test get user and addUser Remove User 
            
            String username = "test1";
            String password = "123";
            
            String input = "{username:"+username+",password:"+password+"}";
            String output = "test1";

            testAddUser(input, output);
            userDB.removeUser(username);

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    
    
    @Test
    public void testRemoveUser() throws Exception{
        try {
            UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            User u2 = new User("test1","123");
            u2.setPasswordHash("123");
            u2.setUsername("test1");
            
            //test get user and addUser Remove User 
            
            String username = "test1";
            
            String input = "{username:"+username+"}";
            String output = "";
            
            userDB.addUser(u1);

            testRemoveUser(input, output);
          

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    
    @Test
    public void testRemoveUserWrongUsername() throws Exception{
        try {
            UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            User u2 = new User("test1","123");
            u2.setPasswordHash("123");
            u2.setUsername("test1");
            
            //test get user and addUser Remove User 
            
            String username = "test1";
            
            String incoming = "{username:"+username+"}";
            String outgoing = "";
            
            try {
                RemoveUserHandler handler  = new RemoveUserHandler();
                InputStream input = new ByteArrayInputStream(incoming.getBytes());
                OutputStream output = new ByteArrayOutputStream();
               
                handler.handleRequest(input, output, createContext("add user"));
                JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

                assertEquals(outputNode.get("logMsg").asText(), "Failed to remove user");
            } catch (Exception e) {
                fail("Invalid"+ e);
                System.out.println("error: " +e);
            }
          

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    @Test
    public void LoginTestCorrect() throws Exception{
        try {
            UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            User u2 = new User("test1","123");
            u2.setPasswordHash("123");
            u2.setUsername("test1");
            
            //test get user and addUser Remove User 
            

            String username = "test1";
            String password = "123";
            
            String input = "{username:"+username+",password:"+password+"}";
            String output = "test1";
            
            userDB.addUser(u1);
            Login(input, output);
            userDB.removeUser(username);

          

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    @Test
    public void LoginWrongPassword() throws Exception{
        try {
            UserDAO userDB = new UserDAO();
            
            User u1 = new User("test1","123",false);
            User u2 = new User("test1","123");
            u2.setPasswordHash("123");
            u2.setUsername("test1");
            
            //test get user and addUser Remove User 
            

            String username = "test1";
            String password = "1";
            
            String incoming = "{username:"+username+",password:"+password+"}";
            String outgoing = "test1";
            
            userDB.addUser(u1);
            try {
                HandleLogin handler  = new HandleLogin();
                InputStream input = new ByteArrayInputStream(incoming.getBytes());
                OutputStream output = new ByteArrayOutputStream();
               
                handler.handleRequest(input, output, createContext("add user"));
                JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

                assertEquals(outputNode.get("logMsg").asText(), "Wrong Password");
            } catch (Exception e) {
                fail("Invalid"+ e);
                System.out.println("error: " +e);
            }
            userDB.removeUser(username);

          

        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
    }
    
    
    
    @Test
    public void LoginTestWrongUser() throws Exception{
    	
            String username = "test1";
            String password = "123";
            
            String incoming = "{username:"+username+",password:"+password+"}";
            String outgoing = "test1";
            

            try {
                HandleLogin handler  = new HandleLogin();
                InputStream input = new ByteArrayInputStream(incoming.getBytes());
                OutputStream output = new ByteArrayOutputStream();
               
                handler.handleRequest(input, output, createContext("add user"));
                JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
               
                assertEquals(outputNode.get("logMsg").asText(), "Invalid User");
            } catch (Exception e) {
                fail("Invalid"+ e);
                System.out.println("error: " +e);
            }
          
    }
    
   
    @Test
    public void GetUsers() throws Exception{
          
    	String incoming = "{}";
        String outgoing = "";
    		
        try {
            GetAllUsersHandler handler  = new GetAllUsersHandler();
            InputStream input = new ByteArrayInputStream(incoming.getBytes());
            OutputStream output = new ByteArrayOutputStream();
            handler.handleRequest(input, output, createContext("get users"));
            JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

            assertEquals(outputNode.get("logMsg").asText(), "List Of Users");
        } catch (Exception e) {
            fail("Invalid"+ e);
            System.out.println("error: " +e);
        }
          
    }
    //"Failed to Get user data"
   
}