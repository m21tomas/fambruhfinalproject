package coms.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.extra.FAQ;
import coms.model.extra.FAQcategory;
public interface FAQRepository extends JpaRepository<FAQ,Long> {
	List<FAQ> findAllByCategory_Id(int id);


}
