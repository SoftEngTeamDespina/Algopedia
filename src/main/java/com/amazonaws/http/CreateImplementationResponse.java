ackage com.amazonaws.http;

public class CreateImplementationResponse {
	String implemantationID;
	int statusCode;
	String errorMessage;

	public CreateImplementationResponse(String implemantationID, int statusCode){
		this.implemantationID = implemantationID;
		this.statusCode = statusCode;
		this.errorMessage = "";

	}

	public CreateImplementationResponse(int statusCode, String errorMessage){
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;

	}

	public String toString() {
		if (statusCode / 100 == 2) {  
			return "CreateImplementation(" + implemantationID + ")";
		} else {
			return "ErrorResult(statusCode=" + statusCode + ", err=" + errorMessage + ")";
		}
	}

}
