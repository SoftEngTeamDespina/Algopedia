package com.amazonaws.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.entities.Classification;


public class ClassificationDB {
	java.sql.Connection conn;
	final String tblName = "classification";
	
	public ClassificationDB() {
		
	}
	
	public boolean addClassification(Classification cl) throws Exception{
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name = ?;");
            ps.setString(1, cl.getClassificationID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Classification c = new Classification();
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
            ps.setString(1,  cl.getClassificationID());
            ps.setString(2,  cl.getName());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create classification: " + e.getMessage());
        }
		
		//return true;
	}

}
