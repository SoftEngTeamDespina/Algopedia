package com.amazonaws.db;

import java.sql.*;

import com.amazonaws.entities.Algorithm;




public class AlgorithmDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "algorithm";   // Exact capitalization

    public AlgorithmDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    

    //to fix
    public Algorithm getAlgorithm(String name) throws Exception {
        
        try {
            Algorithm algo = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                algo = new Algorithm(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"), resultSet.getString("classification"));
            }
            resultSet.close();
            ps.close();
            
            return algo;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting algorithm: " + e.getMessage());
        }
    }
    
    public boolean addAlgorithm(Algorithm algo) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, algo.getName());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,name,description,classification) values(UUID(),?,?,?);");
            ps.setString(1, algo.getName());
            ps.setString(2, algo.getDescription());
            ps.setString(3, algo.getClassificationID());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create algorithm: " + e.getMessage());
        }
    }
    
    
    
   

}


