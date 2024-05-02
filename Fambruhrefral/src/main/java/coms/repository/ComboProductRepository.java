package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.product.ComboProduct;

public interface ComboProductRepository extends JpaRepository<ComboProduct, Long> {
    // Add custom query methods if needed
}
