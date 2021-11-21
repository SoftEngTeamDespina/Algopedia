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
    
public LinkedList<ProblemInstance> getProblemInstanceByAlgorithm(String algorithm) throws Exception {
		LinkedList<ProblemInstance> ret = new LinkedList<ProblemInstance>();
        try {
        	//get algorithm id using name
        	PreparedStatement p = conn.prepareStatement("SELECT * FROM algorithm WHERE name=?;");
        	p.setString(1, algorithm);
            ResultSet resultSet2 = p.executeQuery();
            String algo_id = "";
            while (resultSet2.next()) {
            	algo_id = resultSet2.getString("UID");
            }
            
            ProblemInstance pi = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=?;");
            ps.setString(1, algo_id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pi = new ProblemInstance(resultSet.getString("UID"),resultSet.getString("name"),resultSet.getString("description"),resultSet.getString("filename"));
                ret.add(pi);
            }
            resultSet.close();
            ps.close();
            
            return ret;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

}


