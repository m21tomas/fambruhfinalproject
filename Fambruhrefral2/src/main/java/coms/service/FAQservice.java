package coms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms.model.blog.Blog;
import coms.model.extra.FAQ;
import coms.model.extra.FAQcategory;
import coms.repository.Blogrepository;
import coms.repository.FAQRepository;
import coms.repository.FAQcategoryRepo;

@Service
public class FAQservice {
	  @Autowired
	    private FAQRepository faqRepository;

	  @Autowired
	  private FAQcategoryRepo faqcatrepo;
	  
		public List<FAQcategory> getAllCategories(){
			
			return faqcatrepo.findAll();
			
		}
		
		public void addCategory(FAQcategory category) {
			
			faqcatrepo.save(category);
		}
		
		public void removeCategoryById(int id) {
			faqcatrepo.deleteById(id);
		}
		
		
		public Optional<FAQcategory> getCatById(int id) {
			
			return faqcatrepo.findById(id);
			
		}
		
		public List<FAQ> getAllProductsByCategoryId(int id){
			return faqRepository.findAllByCategory_Id(id);
			
		}
	  
	    // Service method to retrieve all blog posts
	    public List<FAQ> getAllFAQs() {
	        // Add any necessary business logic here
	        return faqRepository.findAll();
	    }

	    // Service method to retrieve a blog post by its ID
	    public FAQ getFAQById(Long id) {
	        // Add any necessary business logic here
	        return faqRepository.findById(id).orElse(null);
	    }

	 

	    // Service method to save a new blog post
	    public FAQ saveFAQ(FAQ faq) {
	        // Set the post date to the current date when saving
	     
	        // Add any necessary business logic here
	        return faqRepository.save(faq);
	    }

	    // Service method to update an existing blog post
	    public FAQ updateFAQ(Long id, FAQ faq) {
	        // Add any necessary business logic here
	        FAQ existingfaq = faqRepository.findById(id).orElse(null);
	        if (existingfaq != null) {
	            // Update the existing blog post with the new data
	        	existingfaq.setQuestion(faq.getQuestion());
	        	existingfaq.setAnswer(faq.getAnswer());
	        	existingfaq.setExample(faq.getExample());
	        	existingfaq.setOthers(faq.getOthers());
	 
	            // Add any other fields to update
	            return faqRepository.save(existingfaq);
	        }
	        return null;
	    }

	    // Service method to delete a blog post by its ID
	    public void deleteFAQById(Long id) {
	        // Add any necessary business logic here
	        faqRepository.deleteById(id);
	    }

	  
}
