package com.amazonaws.db;

import java.sql.*;
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
    

    //to fix
    public Implementation getImplementation(String UID) throws Exception {
        
        try {
        	Implementation imp = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID=?;");
            ps.setString(1,  UID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	//need to set constructors for Implementation
                //imp = new Implementation(resultSet.getString("username"),resultSet.getString("passwordHash"),resultSet.getBoolean("isAdmin"));
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
            ps.setString(2, imp.getCode().toString());
            //do we want our entities to have the same structure as the db?
            //ps.setString(3, imp.getAlgorithm());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create implementation: " + e.getMessage());
        }
    }
    
    
    
   

}


