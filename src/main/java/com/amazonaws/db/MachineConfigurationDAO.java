package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.Classification;
import com.amazonaws.entities.MachineConfiguration;



public class MachineConfigurationDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "machine_config";   // Exact capitalization

    public MachineConfigurationDAO() {
    	System.out.print("machine_config DAO here");
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
	   
    public MachineConfiguration getMachineConfig(String UID) throws Exception {
        
        try {
            MachineConfiguration mc = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID=?;");
            ps.setString(1,  UID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                mc = new MachineConfiguration(resultSet.getString("UID"),resultSet.getString("cpu"),resultSet.getInt("cores"),resultSet.getInt("threads"),resultSet.getString("cacheL1"),resultSet.getString("cacheL2"),resultSet.getString("cache_smart"));
            }
            resultSet.close();
            ps.close();
            
            return mc;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }
    
    
   

}


