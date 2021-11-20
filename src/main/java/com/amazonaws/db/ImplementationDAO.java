package com.amazonaws.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.entities.Implementation;



public class ImplementationDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "implementation";   // Exact capitalization

    public ImplementationDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    

    public Implementation getImplementation(String filename) throws Exception {
        
        try {
        	Implementation imp = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE filename=?;");
            ps.setString(1,  filename);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                imp = new Implementation(resultSet.getString("UID"),resultSet.getString("language"),resultSet.getString("filename"),resultSet.getString("algorithm"));
            }
            resultSet.close();
            ps.close();
            
            return imp;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting implementation: " + e.getMessage());
        }
    }
    
    
    public boolean addImplementation(Implementation imp) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID = ?;");
            ps.setString(1, imp.getImplementationID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,language,filename,algorithm) values(UUID(),?,?,?);");
            ps.setString(1, imp.getLanguage().toString());
            ps.setString(2, imp.getFileName().toString());
            ps.setString(3, imp.getAlgorithmID());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create implementation: " + e.getMessage());
        }
    }
    
    public LinkedList<Implementation> getAllImplementations(String algoID) throws Exception {
        
        try {
        	Implementation imp = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=?;");
            ps.setString(1,  algoID);
            ResultSet resultSet = ps.executeQuery();
            
            LinkedList<Implementation> imps = new LinkedList<Implementation>();
            while (resultSet.next()) {
                imp = new Implementation(resultSet.getString("UID"),resultSet.getString("language"),resultSet.getString("filename"),resultSet.getString("algorithm"));
                imps.add(imp);
            }
            
            resultSet.close();
            ps.close();
            
            return imps;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting implementations: " + e.getMessage());
        }
    }
    
    
    public boolean deleteImplementation(String implementationID) throws Exception {
        
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE UID=?;");
            ps.setString(1,  implementationID);
            int result = ps.executeUpdate();
            ps.close();
            
            return (result==1);

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in deleting implementation: " + e.getMessage());
        }
    }
    
    
    
    
   

}


