package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coms.model.cartorder.ComboProductQuantity;

public interface ComboQuantityRepo extends JpaRepository<ComboProductQuantity, Long> {

}
