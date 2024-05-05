package coms.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms.model.cartorder.CartOrder;
import coms.model.cartorder.CartOrderBack;
import coms.model.cartorder.UserOrder;
import coms.model.product.ProductImageMain;
import coms.model.product.ProductQuantity;
import coms.repository.OrderRepo;
import coms.service.UserOrderService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
public class UserOrderController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
    private OrderRepo orderRepo;
	
	//FROM YOUR FRONTEND
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@Valid @RequestBody CartOrder cartOrder, Principal principal){
		return userOrderService.saveOrder(cartOrder, principal);
	}
	
	//my with bad CartItem REQUIRING AUTHENTICATION
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/create/frombackend")
	public ResponseEntity<?> createOrderByBackendCartItem(@Valid @RequestBody CartOrderBack cartOrder, Principal principal){
	    return userOrderService.saveOrder2(cartOrder, principal);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/changeStatus/{oid}")
	public ResponseEntity<?> changeUserOrderStatus(@PathVariable("oid") Long oid, @RequestParam String status){
		return userOrderService.changeUserOrderStatus(oid, status);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get/all")
	public ResponseEntity<?> getAllOrders(){
		List<UserOrder> orders = this.userOrderService.getAll();
		return ResponseEntity.ok(orders);
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping("/get/byUsername/{username}")
	public ResponseEntity<?> userOrders(@PathVariable("username") String username){
		List<UserOrder> orders = this.userOrderService.getUserOrders(username);
		if(orders.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.ok(orders);
		}
	}
	
	@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
	@GetMapping("/get/orderInvoice/{oid}")
	public ResponseEntity<?> getUserOrderById(@PathVariable("oid") Long oid){
		UserOrder order = this.userOrderService.getOrderById(oid);
		Set<ProductQuantity> products = order.getProducts();
		products.forEach(p -> {
			ProductImageMain img1 = new ProductImageMain();
		
			img1.setName(p.getProduct().getMainImage().getName());
			img1.setImgId(p.getProduct().getMainImage().getImgId());
			img1.setType(p.getProduct().getMainImage().getType());
			p.getProduct().setMainImage(img1);
		});
		order.setProducts(products);
		return ResponseEntity.ok(order);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{oid}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("oid") Long oid){
		Optional<UserOrder> foundOrder = orderRepo.findById(oid);
		if(foundOrder.isPresent()) {			
			userOrderService.deleteOrder(oid);
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", LocalDateTime.now());
	        body.put("message", "No order found by the provided id to be deleted.");
	        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}
	}
}
