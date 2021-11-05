package com.amazonaws.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.entities.User;



public class UserDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "users";   // Exact capitalization

    public UserDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public User getUser(String name) throws Exception {
        
        try {
            User usr = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE username=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                usr = new User(resultSet.getString("username"),resultSet.getString("passwordHash"),resultSet.getBoolean("isAdmin"));
            }
            resultSet.close();
            ps.close();
            
            return usr;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting user: " + e.getMessage());
        }
    }
    
    public boolean addUser(User user) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE username = ?;");
            ps.setString(1, user.getUsername());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,username,passwordHash,isAdmin) values(UUID(),?,?,?);");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setBoolean(3, user.getIsAdmin());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert User: " + e.getMessage());
        }
    }
    
    
    
   

}