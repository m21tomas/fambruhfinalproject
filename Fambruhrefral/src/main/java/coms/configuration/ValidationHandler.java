package coms.configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import coms.exceptions.ExistingUserException;
import coms.exceptions.MaxPersonCountException;
import coms.exceptions.TokenValidationTimeException;
import coms.exceptions.UnauthorisedRequestException;
import coms.exceptions.UnverifiedUserException;
import coms.exceptions.UserNotFoundException;


@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistingUserException.class)
	public ResponseEntity<Object> handleExistingUserException(ExistingUserException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<Object> handleMessagingException(MessagingException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(TokenValidationTimeException.class)
	public ResponseEntity<Object> handleTokenValidationTimeException(TokenValidationTimeException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UnverifiedUserException.class)
	public ResponseEntity<Object> handleUnverifiedUserException(UnverifiedUserException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(MaxPersonCountException.class)
	public ResponseEntity<Object> handleMaxPersonCountException(MaxPersonCountException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(UnauthorisedRequestException.class)
	public ResponseEntity<Object> handleUnauthorisedRequestException(UnauthorisedRequestException el) {
		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", el.getMessage());
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}
	
}
