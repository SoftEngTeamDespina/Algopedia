package com.amazonaws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;

import org.junit.Test;

import com.amazonaws.db.AlgorithmDAO;
import com.amazonaws.db.BenchmarkDAO;
import com.amazonaws.db.ClassificationDAO;
import com.amazonaws.db.ImplementationDAO;
import com.amazonaws.db.MachineConfigurationDAO;
import com.amazonaws.db.ProblemInstanceDAO;
import com.amazonaws.db.UserDAO;
import com.amazonaws.entities.Algorithm;
import com.amazonaws.entities.Benchmark;
import com.amazonaws.entities.Classification;
import com.amazonaws.entities.Implementation;
import com.amazonaws.entities.MachineConfiguration;
import com.amazonaws.entities.ProblemInstance;
import com.amazonaws.entities.User;
import com.amazonaws.http.CreateBenchmarkResponse;
import com.amazonaws.http.CreateClassificationResponse;
import com.amazonaws.http.CreateProblemInstanceResponse;
import com.amazonaws.http.GetBenchmarkResponse;
import com.amazonaws.http.GetImplementationsAllResponse;
import com.amazonaws.http.GetProblemInstancesAllResponse;
import com.amazonaws.http.MergeClassificationResponse;
import com.amazonaws.http.ReclassifyAlgorithmResponse;
import com.amazonaws.http.RegisterUserResponse;
import com.amazonaws.http.RemoveAlgorithmResponse;
import com.amazonaws.http.RemoveBenchmarkResponse;
import com.amazonaws.http.RemoveClassificationResponse;
import com.amazonaws.http.RemoveImplementationResponse;
import com.amazonaws.http.RemoveProblemInstanceResponse;
import com.amazonaws.http.RemoveUserResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;

public class BenchmarkTests {

	Context createContext(String apiCall){
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}

	@Test
	public void testCreateBenchmark() throws Exception{
		try {
			UserDAO userDB = new UserDAO();
			AlgorithmDAO algodb = new AlgorithmDAO();
			ClassificationDAO classdb = new ClassificationDAO();
			ProblemInstanceDAO pidb = new ProblemInstanceDAO();

			ImplementationDAO imdb = new ImplementationDAO();

			Classification testClass = new Classification("test1", "desc", null);
			classdb.addClassification(testClass);
			String testID = classdb.getClassification("test1").getClassificationID();
			Algorithm algo =  new Algorithm("testAlgo1", "desc", testID);
			algodb.addAlgorithm(algo);
			String algoID = algodb.getAlgorithm("testAlgo1").getAlgorithmID();

			ProblemInstance pi = new ProblemInstance("test1","description","data",algoID);
			pidb.addProblemInstance(pi);
			String piID = pidb.getProblemInstanceNoID(algoID, "test1").getProblemInstanceID();


			Implementation imp = new Implementation("0","0","0",algoID);
			imdb.addImplementation(imp);
			String Impid = imdb.getImplementationByStamp("0").getImplementationID();



			User u1 = new User("test1","123",false);

			userDB.addUser(u1);
			//test get user and addUser Remove User


			String incoming =  "{\"problem_instance\": \""+piID+"\",\"implementation\":\""+Impid+"\",\"user\": \"test1\",\"runtime\": 3,\"name\":\"test1\",\"cores\":3,\"L3\":\"l3\",\"L2\":\"l2\",\"L1\":\"l1\",\"threads\":2,\"cpu\":\"i7\",\"observation\":\"obs\"}";
			String outgoing = "";
			CreateClassificationResponse r = new CreateClassificationResponse(200,"");
			CreateClassificationResponse r2 = new CreateClassificationResponse("",500);
			r.toString();r2.toString();
			
			RemoveBenchmarkResponse rr = new RemoveBenchmarkResponse(200,"");
			RemoveBenchmarkResponse rr2 = new RemoveBenchmarkResponse(500,"");
			rr.toString();rr2.toString();
			
			RemoveProblemInstanceResponse rir = new RemoveProblemInstanceResponse(200,"");
			RemoveProblemInstanceResponse rir2 = new RemoveProblemInstanceResponse(500,"");
			rir.toString();rir2.toString();
			
			RemoveClassificationResponse rcr = new RemoveClassificationResponse(200,"");
			RemoveClassificationResponse rcr2 = new RemoveClassificationResponse(500,"");
			rcr.toString(); rcr2.toString();
			
			RemoveAlgorithmResponse rar = new RemoveAlgorithmResponse(200,"");
			RemoveAlgorithmResponse rar2 = new RemoveAlgorithmResponse(500,"");
			rar.toString();rar2.toString();
			
			ReclassifyAlgorithmResponse rear = new  ReclassifyAlgorithmResponse(200,"");
			ReclassifyAlgorithmResponse rear2 = new  ReclassifyAlgorithmResponse(500,"");
			rear.toString();rear2.toString();
			
			MergeClassificationResponse MCR = new MergeClassificationResponse(200,"");
			MergeClassificationResponse MCR2 = new MergeClassificationResponse(500,"");
			MCR.toString();MCR2.toString();
			
			RemoveUserResponse RUR = new RemoveUserResponse();
			RemoveUserResponse RUR2 = new RemoveUserResponse("",500,"");
			RUR2.toString();
			
			CreateProblemInstanceResponse CPIR = new CreateProblemInstanceResponse("",200,"");
			CreateProblemInstanceResponse CPIR2 = new CreateProblemInstanceResponse("",500,"");
			CPIR.toString();CPIR2.toString();
			
			RemoveImplementationResponse RIM = new RemoveImplementationResponse(200,"");
			RemoveImplementationResponse RIM2 = new RemoveImplementationResponse(500,"");
			RIM.toString();RIM2.toString();
			
			RegisterUserResponse RURr = new RegisterUserResponse(null,200,"");
			RegisterUserResponse RURr2 = new RegisterUserResponse(null,500,"");
			RURr.toString();RURr2.toString();
			
			GetProblemInstancesAllResponse GPIA = new GetProblemInstancesAllResponse(200,"");
			GetProblemInstancesAllResponse GPIA2 = new GetProblemInstancesAllResponse(500,"");
			GPIA.toString();GPIA.toString();
			
			GetImplementationsAllResponse GIAR = new GetImplementationsAllResponse(200,"");
			GetImplementationsAllResponse GIAR2 = new GetImplementationsAllResponse(500,"");
			GIAR.toString();GIAR2.toString();
			
			
			

			try {
				CreateBenchmarkHandler handler  = new CreateBenchmarkHandler();
				InputStream input = new ByteArrayInputStream(incoming.getBytes());
				OutputStream output = new ByteArrayOutputStream();

				handler.handleRequest(input, output, createContext("add user"));
				JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

				assertEquals(outputNode.get("statusCode").asText(), "200");
			} catch (Exception e) {
				fail("Invalid"+ e);
				System.out.println("error: " +e);
			}

			userDB.removeUser("test1");
			pidb.removeProblemInstance(piID);
			imdb.deleteImplementation(Impid);
			algodb.removeAlgorithm(algoID);
			classdb.removeClassification(testID);



		} catch (Exception e) {
			fail("Invalid"+ e);
			System.out.println("error: " +e);
		}
	}

	@Test
	public void tesGetAllBenchmarks200() throws Exception{


		UserDAO userDB = new UserDAO();
		AlgorithmDAO algodb = new AlgorithmDAO();
		ClassificationDAO classdb = new ClassificationDAO();
		ProblemInstanceDAO pidb = new ProblemInstanceDAO();
		MachineConfigurationDAO mb = new MachineConfigurationDAO();
		ImplementationDAO imdb = new ImplementationDAO();
		
		CreateBenchmarkResponse res = new CreateBenchmarkResponse("",200);
		CreateBenchmarkResponse res2 = new CreateBenchmarkResponse(500,"");
		res.toString();res2.toString();
		
		GetBenchmarkResponse ben = new GetBenchmarkResponse(null,200);		
		GetBenchmarkResponse ben2 = new GetBenchmarkResponse(500,"");		
		ben.toString();ben2.toString();
		
		Classification testClass = new Classification("test1", "desc", null);
		classdb.addClassification(testClass);
		String testID = classdb.getClassification("test1").getClassificationID();
		Algorithm algo =  new Algorithm("testAlgo1", "desc", testID);
		algodb.addAlgorithm(algo);
		String algoID = algodb.getAlgorithm("testAlgo1").getAlgorithmID();

		
		ProblemInstance pi = new ProblemInstance("test1","description","data",algoID);
		pidb.addProblemInstance(pi);
		String piID = pidb.getProblemInstanceNoID(algoID, "test1").getProblemInstanceID();
		long now = System.currentTimeMillis();
		Timestamp stamp = new Timestamp(now);

		Implementation imp = new Implementation("0","0","0",algoID);
		imdb.addImplementation(imp);
		String Impid = imdb.getImplementationByStamp("0").getImplementationID();
		imp.setImplementationID(Impid);
		
		
		MachineConfiguration m = new MachineConfiguration(stamp.toString(),"",0,0,"","","");
		mb.addMachineConfig(m);
		
		
		String incoming  = "{algorithm:'testAlgo1'}";
		try {
			GetAllBenchmarksHandler handler  = new GetAllBenchmarksHandler();
			InputStream input = new ByteArrayInputStream(incoming.getBytes());
			OutputStream output = new ByteArrayOutputStream();

			handler.handleRequest(input, output, createContext("add user"));
			JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

			assertEquals(outputNode.get("statusCode").asText(), "200");
		} catch (Exception e) {
			fail("Invalid"+ e);
			System.out.println("error: " +e);
		}
		algodb.removeAlgorithm(algoID);
		classdb.removeClassification(testID);


	}

	@Test
	public void tesGetAllBenchmarks500() throws Exception{
		


		String incoming  = "{algorithm:'qwe'}";
		try {
			GetAllBenchmarksHandler handler  = new GetAllBenchmarksHandler();
			InputStream input = new ByteArrayInputStream(incoming.getBytes());
			OutputStream output = new ByteArrayOutputStream();

			handler.handleRequest(input, output, createContext("add user"));
			JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

			assertEquals(outputNode.get("statusCode").asText(), "500");
		} catch (Exception e) {
			fail("Invalid"+ e);
			System.out.println("error: " +e);
		}



	}


	@Test
	public void testRemoveBenchmark() throws Exception{
		try {
			UserDAO userDB = new UserDAO();
			AlgorithmDAO algodb = new AlgorithmDAO();
			ClassificationDAO classdb = new ClassificationDAO();
			ProblemInstanceDAO pidb = new ProblemInstanceDAO();
			BenchmarkDAO benchdb = new BenchmarkDAO();
			MachineConfigurationDAO mb = new MachineConfigurationDAO();
			

			ImplementationDAO imdb = new ImplementationDAO();

			Classification testClass = new Classification("test1", "desc", null);
			classdb.addClassification(testClass);
			String testID = classdb.getClassification("test1").getClassificationID();
			Algorithm algo =  new Algorithm("testAlgo1", "desc", testID);
			algodb.addAlgorithm(algo);
			String algoID = algodb.getAlgorithm("testAlgo1").getAlgorithmID();

			ProblemInstance pi = new ProblemInstance("test1","description","data",algoID);
			pidb.addProblemInstance(pi);
			String piID = pidb.getProblemInstanceNoID(algoID, "test1").getProblemInstanceID();
			long now = System.currentTimeMillis();
			Timestamp stamp = new Timestamp(now);

			Implementation imp = new Implementation("0","0","0",algoID);
			imdb.addImplementation(imp);
			String Impid = imdb.getImplementationByStamp("0").getImplementationID();
			imp.setImplementationID(Impid);
			
			
			MachineConfiguration m = new MachineConfiguration(stamp.toString(),"",0,0,"","","");
			mb.addMachineConfig(m);
			
			



			User u1 = new User("test1","123",false);

			userDB.addUser(u1);
			//test get user and addUser Remove User
			//public Benchmark(String name, Implementation implementation2 ,MachineConfiguration configuration, ProblemInstance instance,
			//double runtime, String observations) {
			Benchmark b = new Benchmark("test1",imp,mb.getMachineConfigByStamp(stamp),pi,0,"");
			benchdb.addBenchmark(b);
			String bid =  benchdb.getBenchmark("test1").getBenchmarkID();


			String incoming =  "{id:"+bid+", user:'test1'}";

			try {
				RemoveBenchmarkHandler handler  = new RemoveBenchmarkHandler();
				InputStream input = new ByteArrayInputStream(incoming.getBytes());
				OutputStream output = new ByteArrayOutputStream();

				handler.handleRequest(input, output, createContext("add user"));
				JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);

				assertEquals(outputNode.get("statusCode").asText(), "200");
			} catch (Exception e) {
				fail("Invalid"+ e);
				System.out.println("error: " +e);
			}

			userDB.removeUser("test1");
			pidb.removeProblemInstance(piID);
			imdb.deleteImplementation(Impid);
			algodb.removeAlgorithm(algoID);
			classdb.removeClassification(testID);


		} catch (Exception e) {
			fail("Invalid"+ e);
			System.out.println("error: " +e);
		}
	}
	


}
