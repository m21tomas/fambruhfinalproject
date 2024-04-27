package coms.exceptions;

public class UnauthorisedRequestException extends RuntimeException {
	private static final long serialVersionUID = -2110474785807135410L;
	
	private String message;

	public UnauthorisedRequestException() {}
	public UnauthorisedRequestException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
