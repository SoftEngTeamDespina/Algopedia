package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.UserAction;



public class UserActionDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "userAction";   // Exact capitalization

    public UserActionDAO() {
    	// System.out.println("user action DAO here");
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
	 public LinkedList<UserAction> getUserActions(String username) throws Exception {
		 
		 	LinkedList<UserAction> returnList = new LinkedList<UserAction>();
	        
	        try {
	        	UserAction ua = null;
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM  userAction  WHERE author=?;");
	            ps.setString(1, username);
	            ResultSet resultSet = ps.executeQuery();
	            
	            int i =0;
	            
	            while (resultSet.next()) {
	            	i++;
	                ua = new UserAction(resultSet.getString("UID"),resultSet.getString("author"),resultSet.getString("action"),resultSet.getString("timestamp"));
	                returnList.add(ua);
	            }
	            if(i == 0) {
	            	 throw new Exception("No actions to get");
	            }
	            
	            resultSet.close();
	            ps.close();
	            
	            return  returnList;
	
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Failed in getting user actions: " + e.getMessage());
	        }
	    }
	    
    
   
    
    public boolean addUserAction(UserAction ua) throws Exception {
        
    	try {
           
    		PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,author,action,timestamp) values(UUID(),?,?,?);");
            ps.setString(1, ua.getAuthorID());
            ps.setString(2, ua.getAction());
            ps.setString(3, ua.getTimeStamp());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add user action: " + e.getMessage());
        }
    }
    
    
    
   

}


