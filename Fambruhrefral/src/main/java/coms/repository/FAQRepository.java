package coms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.extra.FAQ;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
	List<FAQ> findAllByCategory_Id(int id);
}
