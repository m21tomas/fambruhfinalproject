package coms.exceptions;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -4542288274206127359L;
	
	private String message;

	public UserNotFoundException() {}
	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
