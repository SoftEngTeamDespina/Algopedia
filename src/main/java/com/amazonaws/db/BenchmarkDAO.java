package com.amazonaws.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.amazonaws.entities.Benchmark;
import com.amazonaws.entities.Implementation;



public class BenchmarkDAO{ 

	java.sql.Connection conn;
	
	final String tblName = "benchmark";   // Exact capitalization

    public BenchmarkDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    //to fix
    public Benchmark getBenchmark(String name) throws Exception {
        
        try {
        	Benchmark bench = null;
        	//create ImplemenationDAO, MachineconfigDAO and ProblemInstanceDAO
        	ImplementationDAO idao = new ImplementationDAO();
        	MachineConfigurationDAO mdao = new MachineConfigurationDAO();
        	ProblemInstanceDAO pdao = new ProblemInstanceDAO();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()){
                bench = new Benchmark(resultSet.getString("UID"),resultSet.getDate("date"),resultSet.getString("name"),idao.getImplementationByID(resultSet.getString("implementation")),mdao.getMachineConfig(resultSet.getString("machine_config")),pdao.getProblemInstance(resultSet.getString("problem_instance")),resultSet.getDouble("runtime"),resultSet.getString("observations"));
            }
            resultSet.close();
            ps.close();
            
            return bench;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting benchmark: " + e.getMessage());
        }
    }
    
public Benchmark getBenchmarkByProblemInstance(String id) throws Exception {
        
        try {
        	Benchmark bench = null;
        	//create ImplemenationDAO, MachineconfigDAO and ProblemInstanceDAO
        	ImplementationDAO idao = new ImplementationDAO();
        	MachineConfigurationDAO mdao = new MachineConfigurationDAO();
        	ProblemInstanceDAO pdao = new ProblemInstanceDAO();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE problem_instance=?;");
            ps.setString(1,  id);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()){
                bench = new Benchmark(resultSet.getString("UID"),resultSet.getDate("date"),resultSet.getString("name"),idao.getImplementationByID(resultSet.getString("implementation")),mdao.getMachineConfig(resultSet.getString("machine_config")),pdao.getProblemInstance(resultSet.getString("problem_instance")),resultSet.getDouble("runtime"),resultSet.getString("observations"));
            }
            resultSet.close();
            ps.close();
            
            return bench;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting benchmark: " + e.getMessage());
        }
    }
    
    public boolean addBenchmark(Benchmark bench) throws Exception {
        
    	try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE UID = ?;");
            ps.setString(1, bench.getBenchmarkID());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (UID,date,runtime,observations,machine_config,implementation,problem_instance,name) values(UUID(),CURDATE(),?,?,?,?,?,?);");
            ps.setDouble(1, bench.getRuntime());
            ps.setString(2, bench.getObservations());
            ps.setString(3, bench.getConfiguration().getMachineConfigurationID());
            ps.setString(4, bench.getImplementation().getImplementationID());
            ps.setString(5, bench.getInstance().getProblemInstanceID());
            ps.setString(6, bench.getName());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create benchmark: " + e.getMessage());
        }
    }

}


