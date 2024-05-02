package coms.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coms.model.dtos.FAQdto;
import coms.model.extra.FAQ;
import coms.model.extra.FAQcategory;
import coms.repository.FAQRepository;
import coms.repository.FAQcategoryRepo;
import coms.service.FAQservice;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/FAQ")
public class FAQcontroller {
	@Autowired
	private FAQservice faqservice;

	@Autowired
	private FAQRepository faqrepo;

	@Autowired
	private FAQcategoryRepo catrepo;

	//Add new faq post
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@PostMapping("/add/newcategory")
	public FAQcategory createCategory(@RequestBody FAQcategory faqcat) {
		return catrepo.save(faqcat);
	}


	@GetMapping("/category/{id}")
	public ResponseEntity<?> viewByCategory(@PathVariable int id) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        response.put("categories", faqservice.getAllCategories());
	        response.put("FAQs", faqservice.getAllFaqsByCategoryId(id));

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("error", "An error occurred: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//Add new faq post
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@PostMapping("/add/new")
	public ResponseEntity<?> createFAQ(@RequestBody FAQdto faq) {
	 FAQcategory category = catrepo.findByName(faq.getCategory());

	 if(category == null){
	     Map<String, Object> body = new LinkedHashMap<>();
	     body.put("timestamp", LocalDateTime.now());
	     body.put("message", "No such category found for your new FAQ");
	     return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	 }
	 else{
	     FAQ newFaq = new FAQ(faq.getQuestion(), faq.getAnswer(), faq.getExample(),
	                          faq.getOthers(), category);
	     FAQ savedFaq = faqrepo.save(newFaq);
	     return ResponseEntity.ok(savedFaq);
	 }
	}


	// Update existing faq post
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFaq(@PathVariable("id") Long id, @Valid @RequestBody FAQ faq) {
	    FAQ existingfaq = faqservice.getFAQById(id);
	    if (existingfaq != null) {
	        existingfaq.setQuestion(faq.getQuestion());
	        existingfaq.setAnswer(faq.getAnswer());
	        existingfaq.setExample(faq.getExample());
	        existingfaq.setOthers(faq.getOthers());

	        // Add any other fields to update
	        faqservice.saveFAQ(existingfaq);
	        return ResponseEntity.status(HttpStatus.OK).build();
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

	// Get faq post by ID
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getFaqById(@PathVariable("FAQid") Long id) {
	    FAQ faq = faqservice.getFAQById(id);
	    if (faq != null) {
	        return ResponseEntity.ok(faq);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

	// Get all faq posts
	@GetMapping("/get/all-faqs")
	public ResponseEntity<?> getAllFaqs() {
	    List<FAQ> allFaqs = faqservice.getAllFAQs();
	    if (allFaqs.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } else {
	        return ResponseEntity.ok(allFaqs);
	    }
	}

	// Delete faq post by ID
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFaq(@PathVariable("FAQid") Long id) {
	    faqservice.deleteFAQById(id);
	    return ResponseEntity.status(HttpStatus.OK).build();
	}
}
