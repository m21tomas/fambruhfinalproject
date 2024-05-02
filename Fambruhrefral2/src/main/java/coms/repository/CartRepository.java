package coms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import coms.model.cartorder.CartItemBack;
import coms.model.user.*;
public interface CartRepository extends JpaRepository<CartItemBack, Long> {
	public List<CartItemBack> findByUser(User user);
	public List<CartItemBack> findByUserUsername(String username);

    void deleteByProductQuantities_Product_Pid(Long productId);

    void deleteByComboProductQuantities_ComboProduct_Id(Long comboProductId);
}