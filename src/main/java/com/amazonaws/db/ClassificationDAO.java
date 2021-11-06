package com.amazonaws.db;

import java.sql.*;


import com.amazonaws.entities.Classification;



public class ClassificationDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "classification";   // Exact capitalization

    public ClassificationDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    

    //to fix
    public Classification getClassification(String name) throws Exception {
        
        try {
            Classification cl = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                cl = new Classification(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("clasificationcol"));
            }
            resultSet.close();
            ps.close();
            
            return cl;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }
    
    public boolean addClassification(Classification cl) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, cl.getName());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,name,description,clasificationcol) values(UUID(),?,?,?);");
            ps.setString(1, cl.getName());
            ps.setString(2, cl.getDescription());
            ps.setString(3, cl.getSuperClassification());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create classification: " + e.getMessage());
        }
    }
    
    
    
   

}


