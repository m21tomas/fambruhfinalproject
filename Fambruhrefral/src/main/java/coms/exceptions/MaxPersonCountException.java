package coms.exceptions;

public class MaxPersonCountException extends RuntimeException {
	private static final long serialVersionUID = -3543925414600269315L;
	
	private String message;
	
	public MaxPersonCountException() {}
	public MaxPersonCountException(String message) {
        super(message);
        this.message = message;
    }
	
	public String getMessage() {
		return message;
	}

}
