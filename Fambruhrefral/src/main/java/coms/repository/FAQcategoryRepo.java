package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.extra.FAQcategory;

public interface FAQcategoryRepo extends JpaRepository<FAQcategory, Integer> {
	FAQcategory findByName(String name);
}
