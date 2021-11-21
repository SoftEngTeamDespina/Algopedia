package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.Classification;
import com.amazonaws.entities.ProblemInstance;


public class ProblemInstanceDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "problem_instance";   // Exact capitalization

    public ProblemInstanceDAO() {
    	System.out.print("problem_instance DAO here");
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }  
    
    public ProblemInstance getProblemInstance(String UID) throws Exception {
        
        try {
            ProblemInstance pi = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID=?;");
            ps.setString(1, UID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                pi = new ProblemInstance(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("filename"));
            }
            resultSet.close();
            ps.close();
            
            return pi;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

}


