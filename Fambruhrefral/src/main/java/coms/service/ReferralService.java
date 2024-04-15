package coms.service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coms.configuration.JwtUtil;
import coms.configuration.RandomStringGenerator;
import coms.exceptions.ExistingUserException;
import coms.exceptions.MaxPersonCountException;
import coms.exceptions.UnauthorisedRequestException;
import coms.exceptions.UserNotFoundException;
import coms.model.dtos.RegisterDto;
import coms.model.dtos.UserInfoResponse;
import coms.model.user.Authority;
import coms.model.user.Role;
import coms.model.user.User;
import coms.model.user.UserRole;
import coms.repository.RoleRepo;
import coms.repository.UserRepo;

@Service
public class ReferralService {
	
	@Value("${defaultRefCount}")
    private int defaultRefCount;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private RandomStringGenerator codeGen;
	
	private UserInfoResponse mapToUserInfoResponse (User user) {
		UserInfoResponse userDto = new UserInfoResponse();
		
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		
		Set<Authority> authorities = user.getAuthorities().stream()
		        .map(grantedAuthority -> new Authority(grantedAuthority.getAuthority()))
		        .collect(Collectors.toSet());
		userDto.setRoles(authorities);
		if (user.getReferralCode() != null && !user.getReferralCode().isEmpty()) {			
			userDto.setReferralCode(user.getReferralCode());
		}
		else {
			userDto.setReferralCode(null);
		}
		if (user.getReferredByCode() != null && !user.getReferredByCode().isEmpty()) {			
			userDto.setReferredByCode(user.getReferredByCode());
		}
		else {
			userDto.setReferredByCode(null);
		}
		return userDto;
	}

	@Transactional(readOnly = true)
	public UserInfoResponse getUserByUsername(String username, Principal principal) {
		User user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UserNotFoundException("User not found!");
		}
		User checker = userRepo.findByUsername(principal.getName());
		
		boolean isAdmin = checker.getAuthorities().stream()
		        .map(GrantedAuthority::getAuthority)
		        .anyMatch(authority -> authority.equals("ADMIN"));
		
		if(principal.getName().equals(username) || isAdmin) {
			return mapToUserInfoResponse(user);
		}
		else {
			throw new UnauthorisedRequestException("You are not authorised to get user's \""+username+"\" information. You can get only your own information.");
		}
	}

	@Transactional(readOnly = true)
	public List<UserInfoResponse> getAllByReferralCode(String referralCode) {
		if (!userRepo.existsUserByReferralCode(referralCode)) {
            throw new UserNotFoundException("No users found with the given referral code!");
        }
		
		List<User> users = userRepo.findAllByReferralCode(referralCode);
		
		List<UserInfoResponse> dtoList = users.stream().map(user -> mapToUserInfoResponse(user)).collect(Collectors.toList());
		
		return dtoList;
	}

	@Transactional(readOnly = true)
	public List<UserInfoResponse> getAllByReferredByCode(String referredByCode, Principal principal) {
		if (!userRepo.existsUserByReferredByCode(referredByCode)) {
            throw new UserNotFoundException("No users found with the given reference code!");
        }
		
		User checkUser = userRepo.findByUsername(principal.getName());
		
		boolean isAdmin = checkUser.getAuthorities().stream()
		        .map(GrantedAuthority::getAuthority)
		        .anyMatch(authority -> authority.equals("ADMIN"));
		
		if(checkUser.getReferralCode().equals(referredByCode) || isAdmin) {
			List<User> users = userRepo.findAllByReferredByCode(referredByCode);
			
			List<UserInfoResponse> dtoList = users.stream().map(user -> mapToUserInfoResponse(user)).collect(Collectors.toList());
			
			return dtoList;
		}
		else {
			throw new UnauthorisedRequestException("The referral code you provided is not yours and you can't check other users referred friends info");
		}
	}
	
	private List<UserInfoResponse> getAllByReferencedCode(String referralCode) {
		if (!userRepo.existsUserByReferralCode(referralCode)) {
            throw new UserNotFoundException("No users found with the given referral code!");
        }
		List<User> users = userRepo.findAllByReferredByCode(referralCode);
		
		List<UserInfoResponse> dtoList = users.stream().map(user -> mapToUserInfoResponse(user)).collect(Collectors.toList());
		
		return dtoList;
    }

	@Transactional
	public String makeUserAreferral(Principal principal) {
		User userByPricipal = (User) userDetailService.loadUserByUsername(principal.getName());
		
		User foundUser = userRepo.findByUsername(userByPricipal.getUsername());
		
		if(foundUser == null) {
			throw new UserNotFoundException(userByPricipal.getUsername()+" user not found!");
		}
		
		if (foundUser.getReferralCode() == null || foundUser.getReferralCode().isEmpty()) {
			foundUser.setReferralCode(generateReferralCode());
			userRepo.save(foundUser);
			return "The referral code for you is generated. You can invite new users with your referral code"; 
		}
		return "You already have referral code!";
	}

	private String generateReferralCode() {
        String generated = "";
        do {
            generated = codeGen.generate();
        } while (userRepo.existsUserByReferralCode(generated));

        return generated;
    }

	@Transactional
	public User createReferencedUser(RegisterDto userDto, String referralCode) throws MessagingException {
		User checkUser = this.userRepo.findByUsername(userDto.getUsername());
		
		if(checkUser != null) {
			throw new ExistingUserException("Username already exists!");
		}
		
		if (referralCode == null || referralCode.isEmpty()) {
            throw new UserNotFoundException("Referral code not found");
        }
		
		int referredUserCount = getAllByReferencedCode(referralCode).size();
		if (referredUserCount >= defaultRefCount) { // Only two refferals may be created because
			throw new MaxPersonCountException("Max number of referencies for new user has been reached!");
        } 

		User newUser = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
		
		newUser.setReferredByCode(referralCode);
		
		newUser.setReferralCode(generateReferralCode());
		
		Role role = new Role();
		role.setRoleName("USER");
		UserRole ur = new UserRole();
		ur.setUser(newUser);
		ur.setRole(role);
		Set<UserRole> userRole = new HashSet<>();
		userRole.add(ur);

		for(UserRole uR : userRole) {
			this.roleRepo.save(uR.getRole());
		}

		newUser.getUserRoles().addAll(userRole);

		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		User createdUser = userRepo.save(newUser);
		
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
