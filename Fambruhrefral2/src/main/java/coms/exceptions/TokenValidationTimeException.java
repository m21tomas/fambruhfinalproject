package coms.exceptions;

public class TokenValidationTimeException extends RuntimeException {
	private static final long serialVersionUID = 1841340219663540862L;

	private String message;

	public TokenValidationTimeException() {}
	public TokenValidationTimeException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
