package coms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coms.model.dtos.FAQdto;
import coms.model.extra.FAQ;
import coms.model.extra.FAQcategory;
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

	public List<FAQ> getAllFaqsByCategoryId(int id){
		return faqRepository.findAllByCategory_Id(id);
	}

	// Service method to retrieve all faq posts
	public List<FAQ> getAllFAQs() {
		return faqRepository.findAll();
	}

	// Service method to retrieve a faq post by its ID
	public FAQ getFAQById(Long id) {
	    return faqRepository.findById(id).orElse(null);
	}

	// Service method to save a new faq
	public FAQ saveFAQ(FAQ faq) {
	    return faqRepository.save(faq);
	}

	    // Service method to update an existing faq
	public FAQ updateFAQ(Long id, FAQdto faq) {
	    FAQ existingfaq = faqRepository.findById(id).orElse(null);
	    if (existingfaq != null) {
	        existingfaq.setQuestion(faq.getQuestion());
	        existingfaq.setAnswer(faq.getAnswer());
	        existingfaq.setExample(faq.getExample());
	        existingfaq.setOthers(faq.getOthers());

	        return faqRepository.save(existingfaq);
	    }
	    return null;
	}

	// Service method to delete faq by its ID
	public void deleteFAQById(Long id) {
	    faqRepository.deleteById(id);
	}
}
