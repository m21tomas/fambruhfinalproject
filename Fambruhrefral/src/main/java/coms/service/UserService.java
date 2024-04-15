package coms.service;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coms.configuration.JwtUtil;
import coms.exceptions.ExistingUserException;
import coms.exceptions.TokenValidationTimeException;
import coms.exceptions.UserNotFoundException;
import coms.model.dtos.RegisterDto;
import coms.model.user.Role;
import coms.model.user.User;
import coms.model.user.UserRole;
import coms.repository.RoleRepo;
import coms.repository.UserRepo;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//register a new user
	@Transactional
	public User createUser(RegisterDto userDto) throws MessagingException{
		User checkUser = this.userRepo.findByUsername(userDto.getUsername());
		//if user exists or not

		if(checkUser!=null) {
			throw new ExistingUserException("Username already exists!");
		}else {
			//create new user
			User newUser = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
			Role role = new Role();
			role.setRoleName("USER");
			UserRole ur = new UserRole();
			ur.setUser(newUser);
			ur.setRole(role);
			Set<UserRole> userRole = new HashSet<>();
			userRole.add(ur);
			
			//saving roles
			for(UserRole uR : userRole) {
				this.roleRepo.save(uR.getRole());
			}
			
			//setting userRole in user
			newUser.getUserRoles().addAll(userRole);
			
			//encoding password
			newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
			
			User createdUser = this.userRepo.save(newUser);
			
			if(!createdUser.isEnabled()) {
				try {
					String jwtToken = jwtUtil.generateVerifyAccountToken(createdUser);
					emailUtil.sendAccountVerificationEmail(createdUser.getEmail(), jwtToken);
				} catch (MessagingException e) {
					throw new MessagingException("Unable to send email for account verification. "+"\n"+e.getMessage());
				}
			}
			return createdUser;
		}			
	}
	
	@Transactional
	public User createAdmin(RegisterDto userDto){
		User checkUser = this.userRepo.findByUsername(userDto.getUsername());
		//if user exists or not
		
		if(checkUser!=null) {
			throw new ExistingUserException("Admin username already exists!");
		}else {
			//create new admin
			User newAdmin = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
			Role role = new Role();
			role.setRoleName("ADMIN");
			UserRole ur = new UserRole();
			ur.setUser(newAdmin);
			ur.setRole(role);
			Set<UserRole> userRole = new HashSet<>();
			userRole.add(ur);
			
			//saving roles
			for(UserRole uR : userRole) {
				this.roleRepo.save(uR.getRole());
			}
			
			//setting userRole in user
			newAdmin.getUserRoles().addAll(userRole);
			
			//Automatically enabling admin
			newAdmin.setEnabled(true);
			
			//encoding password
			newAdmin.setPassword(this.passwordEncoder.encode(newAdmin.getPassword()));
			
			User createdAdmin = this.userRepo.save(newAdmin);
			return createdAdmin;
		}	
	}
	
	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		User user = this.userRepo.findByUsername(username);
		return user;
	}
	
	@Transactional
	public void deleteUserById(Long userId) {
		this.userRepo.deleteById(userId);
	}

	@Transactional
	public String verifyAccount(String email, String jwtToken) {
		User verifyUser = userRepo.findByEmail(email);
		
		if(verifyUser == null) {
			throw new UserNotFoundException("User not found with this email: " + email);
		}
		
		if(jwtUtil.validateToken(jwtToken, verifyUser)) {
			verifyUser.setEnabled(true);
			User verifiedUser = userRepo.save(verifyUser);
			if(verifiedUser.isEnabled()) {
				String str = "User by the nickname of "+ verifyUser.getUsername() + " account is verified and "
			                 + verifyUser.getUsername() + " can login.";
				return str;
			}
		}
		String badStr = " Email link time for the account verification expired. \n"
				+ "Generate a new email link for your account verification.";
		throw new TokenValidationTimeException(badStr);
	}
	
	@Transactional(readOnly = true)
	public String regenerateAccountVerification(String email) throws MessagingException {
		User checkUser = userRepo.findByEmail(email);
		
		if(checkUser == null) {
			throw new UserNotFoundException("User not found with this email: " + email);
		}
		
		if(!checkUser.isEnabled()) {
			try {
				String jwtToken = jwtUtil.generateVerifyAccountToken(checkUser);
				emailUtil.sendAccountVerificationEmail(checkUser.getEmail(), jwtToken);
			} catch (MessagingException e) {
				throw new MessagingException("Unable to send email for account verification. "+"\n"+e.getMessage());
			}
		}
		
		return "Email sent... please verify account within 2 minutes";
	}

	@Transactional(readOnly = true)
	public String forgotPassword(String email) throws MessagingException {
		User checkUser = userRepo.findByEmail(email);
		
		if(checkUser == null) {
			throw new UserNotFoundException("User not found with this email: " + email);
		}
		
		try {
			String jwtEmailToken = jwtUtil.generatePasswordResetToken(email);
			emailUtil.sendPasswordResetEmail(email, jwtEmailToken);
		} catch (MessagingException e) {
			throw new MessagingException("Unable to send email for password reset. "+"\n"+e.getMessage());
		}
		
		return "Please check your email and press a link to navigate to password reset.";
	}

	@Transactional
	public String resetPassword(String email, String emailToken, String newPassword) {
		User checkUser = userRepo.findByEmail(email);
		
		if(checkUser == null) {
			throw new UserNotFoundException("User not found with this email: " + email);
		}
		
		if(jwtUtil.validateTokenForEmail(emailToken, email)) {
			checkUser.setPassword(passwordEncoder.encode(newPassword));
			
			userRepo.save(checkUser);
			return "New password set successfully. Login with the new password.";
		}
		
		String badStr = "Email link time for the password reset expired. \n"
				+ "Generate a new email link for your password reset.";
		throw new TokenValidationTimeException(badStr);
	}
	
}
