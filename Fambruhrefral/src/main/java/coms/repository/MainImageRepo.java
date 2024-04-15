package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.product.ProductImageMain;

public interface MainImageRepo extends JpaRepository<ProductImageMain, Long> {
 
}
