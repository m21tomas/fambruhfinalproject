package coms.exceptions;

public class UnverifiedUserException extends RuntimeException {

	private static final long serialVersionUID = 699274625387764946L;
	
	private String message;

	public UnverifiedUserException() {}
	public UnverifiedUserException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
