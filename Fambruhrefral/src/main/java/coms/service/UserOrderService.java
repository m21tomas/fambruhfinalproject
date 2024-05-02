package coms.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import coms.model.cartorder.ComboProductQuantity;
import coms.model.cartorder.UserOrder;
import coms.model.product.ProductQuantity;
import coms.repository.ComboQuantityRepo;
import coms.repository.OrderRepo;
import coms.repository.OrderStatus;
import coms.repository.ProductQuantityRepo;

@Service
public class UserOrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductQuantityRepo productQuantityRepo;
    
    @Autowired
    private ComboQuantityRepo comboQuantityRepo;
    
    public UserOrder saveOrder(UserOrder userOrder) {
		UserOrder orderSaved = this.orderRepo.save(userOrder);
		return orderSaved;
	}
    
	public void saveProductQuantity(ProductQuantity productQuantity) {
		this.productQuantityRepo.save(productQuantity);
	}
	
	public void saveComboProductQuantity(ComboProductQuantity comboProductQuantity) {
		this.comboQuantityRepo.save(comboProductQuantity);
	}
	
	public List<UserOrder> getAll(){
		return this.orderRepo.findAll();
	}
	
	public List<UserOrder> getUserOrders(String username){
		List<UserOrder> orders = this.orderRepo.findByUsername(username);
		return orders;
	}
	
	public UserOrder getOrderById(Long oid) {
		UserOrder order = this.orderRepo.findById(oid).get();
		return order;
	}
	@Transactional
	public void deleteOrder(Long oid) {
		this.orderRepo.deleteById(oid);
	}
	
	@Transactional
	public ResponseEntity<?> changeUserOrderStatus(Long oid, String status) {
		Optional<UserOrder> foundOrder = orderRepo.findById(oid);
		
		if(foundOrder.isPresent()) {
			UserOrder theOrder = foundOrder.get();
			theOrder.setStatus(OrderStatus.valueOf(status));
			UserOrder savedOrder = orderRepo.save(theOrder);
			if(savedOrder.getStatus() == OrderStatus.valueOf(status)) {
				return new ResponseEntity<>("Order status is changed to "+status, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Order status is NOT changed to "+status, HttpStatus.BAD_REQUEST);
			}
		}else {
			Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "No order found by the provided order id.");
    		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
	}

}
