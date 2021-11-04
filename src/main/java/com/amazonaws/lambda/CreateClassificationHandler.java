package com.amazonaws.lambda;

import com.amazonaws.db.ClassificationDB;
import com.amazonaws.entities.Classification;
import com.amazonaws.http.CreateClassificationRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class CreateClassificationHandler implements RequestHandler<CreateClassificationRequest,Context> {
	LambdaLogger logger;
	


	@Override
	public Context handleRequest(CreateClassificationRequest request, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestHandler");
		logger.log(request.toString());
		
		request.getClassificationID();
		request.getName();
		request.getSuperClassification();
		request.getDescription();
		
		Classification newCl = new Classification();
		ClassificationDB db = new ClassificationDB();
		
		try {
			db.addClassification(newCl);
		} catch (Exception e){
			
		}
		
		return null;
	}

}

