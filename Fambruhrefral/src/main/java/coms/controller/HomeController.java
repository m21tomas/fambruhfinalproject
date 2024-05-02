package coms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms.model.extra.Contactus;
import coms.model.user.User;
import coms.repository.UserRepo;
import coms.repository.ContactRepository;
import coms.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private ContactRepository contactrepo;
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private UserService userservice;
	
	@PostMapping("/contactus")
	public Contactus createcontactus(@RequestBody Contactus contactus) {
		return contactrepo.save(contactus);
	}
	 @GetMapping("/viewcontact")
	public List<Contactus> getAllcontacts() {
		return contactrepo.findAll();
	}

	@GetMapping("/userlist")
	public List<User> getAllcontactsUsers() {
		return userrepo.findAll();
	}
	
	@DeleteMapping("/userlist/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
		this.userservice.deleteUserById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
			
}
