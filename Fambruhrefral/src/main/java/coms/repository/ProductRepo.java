package coms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.product.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	public List<Product> findByNameContainingIgnoreCaseOrSaltContainingIgnoreCase(String name, String salt);
	
	public List<Product> findByNameAndAvailableTrue(String name);
}