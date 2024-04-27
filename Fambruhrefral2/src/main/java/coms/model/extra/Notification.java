package coms.model.extra;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String newUser;
    private String newUserEmail;
    private String referralUser;
    private String referralCode;
    private LocalDateTime timestamp;
    
    public Notification() {}
    
	public Notification(String newUser, String newUserEmail,  String referralUser, String referralCode,
			LocalDateTime timestamp) {
		super();
		this.newUser = newUser;
		this.newUserEmail = newUserEmail;
		this.referralUser = referralUser;
		this.referralCode = referralCode;
		this.timestamp = timestamp;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNewUser() {
		return newUser;
	}

	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}
	
	
	public String getNewUserEmail() {
		return newUserEmail;
	}

	public void setNewUserEmail(String newUserEmail) {
		this.newUserEmail = newUserEmail;
	}

	public String getReferralUser() {
		return referralUser;
	}

	public void setReferralUser(String referralUser) {
		this.referralUser = referralUser;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
	    return "Dear " + referralUser + ", a new user with the nickname " + newUser + " from email " + newUserEmail +
	           " signed up on " + timestamp + " using your referral link. Your referral code: " + referralCode;
	}
}
