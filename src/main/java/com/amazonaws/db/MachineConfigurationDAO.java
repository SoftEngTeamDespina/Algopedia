package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.Classification;
import com.amazonaws.entities.MachineConfiguration;



public class MachineConfigurationDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "machine_config";   // Exact capitalization

    public MachineConfigurationDAO() {
    	
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
                mc = new MachineConfiguration(resultSet.getString("UID"),resultSet.getString("timestamp"),resultSet.getString("cpu"),resultSet.getInt("cores"),resultSet.getInt("threads"),resultSet.getString("cacheL1"),resultSet.getString("cacheL2"),resultSet.getString("cache_smart"));
            }
            resultSet.close();
            ps.close();
            
            return mc;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting config: " + e.getMessage());
        }
    }
    
	public MachineConfiguration getMachineConfigByStamp(Timestamp stamp) throws Exception {
	        
	        try {
	            MachineConfiguration mc = null;
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE timestamp=?;");
	            ps.setTimestamp(1,  stamp);
	            ResultSet resultSet = ps.executeQuery();
	            
	            while (resultSet.next()) {
	                mc = new MachineConfiguration(resultSet.getString("UID"),resultSet.getString("timestamp"),resultSet.getString("cpu"),resultSet.getInt("cores"),resultSet.getInt("threads"),resultSet.getString("cacheL1"),resultSet.getString("cacheL2"),resultSet.getString("cache_smart"));
	            }
	            resultSet.close();
	            ps.close();
	            
	            return mc;
	
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Failed in getting config: " + e.getMessage());
	        }
	}
    
    public boolean addMachineConfig(MachineConfiguration m) throws Exception {
    	
    	
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID = ?;");
            ps.setString(1, m.getMachineConfigurationID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,cpu,cores,threads,cacheL1,cacheL2,cache_smart,timestamp) values (UUID(),?,?,?,?,?,?,?);");
            ps.setString(1, m.getCpu());
            ps.setInt(2, m.getCores());
            ps.setInt(3,  m.getThreads());
            ps.setString(4, m.getL1());
            ps.setString(5, m.getL2());
            ps.setString(6, m.getL3());
            ps.setString(7, m.getStamp());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create machineConfig: " + e.getMessage());
        }
    }

    	
    	
    
    
   

}


