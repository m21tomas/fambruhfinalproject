package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.product.ProductImageDetail;

public interface DetailImageRepo extends JpaRepository<ProductImageDetail, Long> {
   
}
