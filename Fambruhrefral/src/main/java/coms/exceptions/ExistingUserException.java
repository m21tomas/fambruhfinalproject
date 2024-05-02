package coms.exceptions;

public class ExistingUserException extends RuntimeException{
	private static final long serialVersionUID = 8900413616752586782L;

	private String message;

	public ExistingUserException() {}
	public ExistingUserException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
