package coms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms.service.ReferralService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/referral")
public class ReferralSystemController {
	
	@Autowired 
	ReferralService refService;
	
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username, Principal principal) {
        return ResponseEntity.ok(refService.getUserByUsername(username, principal));
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{referralCode}")
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
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping("/referralFriendsSignups")
    public ResponseEntity<?> checkReferralFriendsSignups(Principal principal) {
        return refService.getReferralNotifications(principal);
    }
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/listByLevel/{level}")
	public ResponseEntity<?> getReferralsByLevel(@PathVariable int level) {
        return ResponseEntity.ok(refService.getUsersByRefLevel(level));
    }
}
