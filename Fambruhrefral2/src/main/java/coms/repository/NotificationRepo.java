package coms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.extra.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
	List<Notification> findByReferralUser(String referralUser);
	
	List<Notification> findByReferralCode(String referralCode);
	
	List<Notification> findByNewUser(String newUser);
}
