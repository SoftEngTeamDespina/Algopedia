package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.Classification;



public class ClassificationDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "classification";   // Exact capitalization

    public ClassificationDAO() {
    	// System.out.println("classification DAO here");
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
	 public LinkedList<Classification> getAllClassifications() throws Exception {
		 
		 	LinkedList<Classification> returnList = new LinkedList<Classification>();
	        
	        try {
	            Classification cl = null;
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName);
	            ResultSet resultSet = ps.executeQuery();
	            
	            while (resultSet.next()) {
	                cl = new Classification(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("classificationcol"));
	                returnList.add(cl);
	            }
	            
	            resultSet.close();
	            ps.close();
	            
	            return  returnList;
	
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Failed in getting classifications: " + e.getMessage());
	        }
	    }
	    
    
    public Classification getClassification(String name) throws Exception {
        
        try {
            Classification cl = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                cl = new Classification(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("classificationcol"));
            }
            resultSet.close();
            ps.close();
            
            return cl;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

    public Classification getClassificationByID(String id) throws Exception {
        
        try {
            Classification cl = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID=?;");
            ps.setString(1,  id);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                cl = new Classification(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("classificationcol"));
            }
            resultSet.close();
            ps.close();
            
            return cl;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }
    
    public boolean changeClassificationParentAll(String keepID, String mergeID) throws Exception {
        try {
            PreparedStatement ps = conn
                    .prepareStatement("UPDATE " + tblName + " SET classificationcol = ? WHERE classificationcol = ?;");
            ps.setString(1, keepID);
            ps.setString(2, mergeID);
            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            throw new Exception("Failed to update parent classification: " + e.getMessage());
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
            
            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,name,description,classificationcol) values(UUID(),?,?,?);");
            ps.setString(1, cl.getName());
            ps.setString(2, cl.getDescription());
            ps.setString(3, cl.getSuperClassification());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create classification: " + e.getMessage());
        }
    }
    
    public boolean removeClassification(String classID) throws Exception {
        
    	try {
            // PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE UID ="+ classID +" ;");
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE UID = ?;");
            ps.setString(1, classID);
            int rows = ps.executeUpdate();

            if(rows == 1){
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new Exception("Failed to remove classification: " + e.getMessage());
        }
    }
    
   

}


