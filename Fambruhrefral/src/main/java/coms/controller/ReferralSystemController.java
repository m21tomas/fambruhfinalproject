package coms.controller;

import java.net.URI;
import java.security.Principal;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import coms.model.dtos.RegisterDto;
import coms.model.user.User;
import coms.service.ReferralService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class ReferralSystemController {
	
	@Autowired 
	ReferralService refService;
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, Principal principal) {
        return ResponseEntity.ok(refService.getUserByUsername(username, principal));
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/referral/{referralCode}")
    public ResponseEntity<?> getAllByReferralCode(@PathVariable String referralCode) {
		return ResponseEntity.ok(refService.getAllByReferralCode(referralCode));
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping("/myReferredFriends/{referralCode}")
    public ResponseEntity<?> getMyReferredFriends(@PathVariable String referralCode, Principal principal) {
		return ResponseEntity.ok(refService.getAllByReferredByCode(referralCode, principal));
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping("/becomeReferral")
    public ResponseEntity<?> makeUserReferral(Principal principal) {
        return ResponseEntity.ok(refService.makeUserAreferral(principal));
    }
	
	
	
}
