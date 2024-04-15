package coms.controller;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import coms.model.dtos.RegisterDto;
import coms.model.user.User;
import coms.service.ReferralService;
import coms.service.UserService;


@RestController
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	ReferralService refService;
	
	@PostConstruct
	public void createAdmin() {
	    User existingAdmin = this.userService.getByUsername("fambruh@army");
	    if (existingAdmin != null) {
	        System.out.println("Admin user with username 'fambruh@army' already exists!");
	        // Handle the situation where the admin user already exists
	    } else {
	    	RegisterDto adminDto = new RegisterDto();
	    	adminDto.setUsername("fambruh@army");
	    	adminDto.setPassword("admin123");
	    	adminDto.setEmail("shaikhjunaidgh@gmail.com");

	        User adminCreated = this.userService.createAdmin(adminDto);
	        System.out.println("Admin username: " + adminCreated.getUsername());
	    }
	}

	//create new user
	@PostMapping("/user/signup")
	public ResponseEntity<?> createNewUser(@Valid @RequestBody RegisterDto userDto) throws MessagingException{
		User createdUser = this.userService.createUser(userDto);
		System.out.println("\nNew user: "+createdUser.getUsername()+" created. Check email to verify account so you can login.\n");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/signup/{referralCode}")
	public ResponseEntity<?> createNewUser(@RequestBody RegisterDto userDto, @PathVariable String referralCode) throws MessagingException{
		User createdUser = refService.createReferencedUser(userDto, referralCode);
		System.out.println("\nNew referemced user: "+createdUser.getUsername()+" created. Check email to verify account so you can login.\n");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/verify-account")
	public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String jwtToken) {
	    return new ResponseEntity<>(userService.verifyAccount(email, jwtToken), HttpStatus.OK);
	}
	
	@GetMapping("/regenerate-Account-Verification-Link")
	  public ResponseEntity<String> regenerateOtp(@RequestParam String email) throws MessagingException {
	    return new ResponseEntity<String>(userService.regenerateAccountVerification(email), HttpStatus.OK);
	}
	
	@GetMapping("/forgot-password")
	public ResponseEntity<String> forgotpassword(@RequestParam String email) throws MessagingException{
		return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
	}
	
	@GetMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String token, @RequestParam String newPassword){
		return new ResponseEntity<>(userService.resetPassword(email, token, newPassword),HttpStatus.OK);
	}
	
	
}
