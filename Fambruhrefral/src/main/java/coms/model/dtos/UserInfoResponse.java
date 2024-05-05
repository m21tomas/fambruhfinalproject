package coms.model.dtos;

import java.util.Set;

import coms.model.user.Authority;

public class UserInfoResponse {
	private Long userId;
	private String username;
	private String email;
	private String referralCode;
    private String referredByCode;
    private Double credits;
    private int refLevel;
    private Set<Authority> roles;
    
    public UserInfoResponse() {}
    
	public UserInfoResponse(Long userId, String username, String email, String referralCode, String referredByCode,
			Double credits, int refLevel, Set<Authority> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.referralCode = referralCode;
		this.referredByCode = referredByCode;
		this.credits = credits;
		this.refLevel = refLevel;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getReferredByCode() {
		return referredByCode;
	}

	public void setReferredByCode(String referredByCode) {
		this.referredByCode = referredByCode;
	}

	public Double getCredits() {
		return credits;
	}

	public void setCredits(Double credits) {
		this.credits = credits;
	}

	public int getRefLevel() {
		return refLevel;
	}

	public void setRefLevel(int refLevel) {
		this.refLevel = refLevel;
	}

	public Set<Authority> getRoles() {
		return roles;
	}

	public void setRoles(Set<Authority> roles) {
		this.roles = roles;
	}
}
