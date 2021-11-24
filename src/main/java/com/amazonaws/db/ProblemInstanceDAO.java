package com.amazonaws.db;

import java.sql.*;
import java.util.LinkedList;

import com.amazonaws.entities.ProblemInstance;

public class ProblemInstanceDAO {

    java.sql.Connection conn;

    final String tblName = "problem_instance"; // Exact capitalization

    public ProblemInstanceDAO() {
        System.out.print("problem_instance DAO here");
        try {
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
                pi = new ProblemInstance(resultSet.getString("UID"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getString("filename"), resultSet.getString("algorithm"));
            }
            resultSet.close();
            ps.close();

            return pi;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed in getting classification: " + e.getMessage());
        }
    }

    public ProblemInstance getProblemInstanceNoID(String algoID, String instName) throws Exception {

        try {
            ProblemInstance pi = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=? AND name=?;"); 
            ps.setString(1, algoID);
            ps.setString(1, instName);           
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                pi = new ProblemInstance(resultSet.getString("UID"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getString("filename"), resultSet.getString("algorithm"));
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
            // get algorithm id using name
            PreparedStatement p = conn.prepareStatement("SELECT * FROM algorithm WHERE name=?;");
            p.setString(1, algorithm);
            ResultSet resultSet2 = p.executeQuery();
            String algo_id = "";
            while (resultSet2.next()) {
                algo_id = resultSet2.getString("UID");
            }
      
            resultSet2.close();
            p.close();
            
            if(algo_id == "") {
            	throw new Exception("could not find id for name " + algorithm);
            }
            
            //GET PROBLEM INSTANCE BY ALGORITHM ID 
            ProblemInstance pi = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm=?;");
            ps.setString(1, algo_id);
            ResultSet resultSet = ps.executeQuery();
            int counter = 0;
            while (resultSet.next()) {
                pi = new ProblemInstance(resultSet.getString("UID"), resultSet.getString("name"),
                        resultSet.getString("description"), resultSet.getString("filename"), resultSet.getString("algorithm"));
            	counter ++;
                ret.add(pi);
            }
            
            resultSet.close();
            ps.close();

            return ret;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting problem instance: " + e.getMessage());
        }
    }

    public boolean addProblemInstance(ProblemInstance inst) throws Exception {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE algorithm = ?;");
            ps.setString(1, inst.getName());
            ResultSet resultSet = ps.executeQuery();

            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement(
                    "INSERT INTO " + tblName + " (UID,name,description,filename,algorithm) values(UUID(),?,?,?,?);");
            ps.setString(1, inst.getName());
            ps.setString(2, inst.getDescription());
            ps.setString(3, inst.getDataSet());
            ps.setString(4, inst.getAlgoID());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create Problem Instance: " + e.getMessage());
        }
    }

    public boolean removeProblemInstance(String instID) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE UID = ?;");
            ps.setString(1, instID);
            int rows = ps.executeUpdate();

            if(rows == 1){
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("Failed to remove problem instance: " + e.getMessage());
        }
    }

    // public boolean removeProblemInstancesByAlgorithm(String algoID) throws Exception {
        
    // 	try {
    //         PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE algorithm = ?;");
    //         ps.setString(1, algoID);
    //         int rows = ps.executeUpdate();

    //         if(rows >= 1){
    //             return true;
    //         }
    //         return false;
    //     } catch (Exception e) {
    //         throw new Exception("Failed to remove problem instances: " + e.getMessage());
    //     }
    // }

}
