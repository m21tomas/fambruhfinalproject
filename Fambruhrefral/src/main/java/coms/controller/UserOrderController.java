package coms.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

import coms.exceptions.UserNotFoundException;
import coms.model.cartorder.CartItem;
import coms.model.cartorder.CartOrder;
import coms.model.cartorder.ComboProductQuantity;
import coms.model.cartorder.UserOrder;
import coms.model.dtos.OrderInvoiceDto;
import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.product.ProductImageMain;
import coms.model.product.ProductQuantity;
import coms.model.user.User;
import coms.repository.CartRepository;
import coms.repository.ComboProductRepository;
import coms.repository.OrderRepo;
import coms.repository.OrderStatus;
import coms.repository.ProductRepo;
import coms.repository.UserRepo;
import coms.service.Cartwishservice;
import coms.service.EmailUtil;
import coms.service.UserDetailService;
import coms.service.UserOrderService;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
public class UserOrderController {
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private Cartwishservice cartService;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
    private OrderRepo orderRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
    private ProductRepo productRepo;
	
	@Autowired
	private ComboProductRepository comboRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/create")
	@Transactional
	public ResponseEntity<?> createOrder(@Valid @RequestBody CartOrder cartOrder, Principal principal){
		System.out.println("\nORDER CONTROLLER REACHED\n");
		User userByPricipal = (User) userDetailService.loadUserByUsername(principal.getName());
		User foundUser = userRepo.findByUsername(userByPricipal.getUsername());

		UserOrder userOrder = new UserOrder();
		userOrder.setUsername(foundUser.getUsername());
		userOrder.setFirstname(cartOrder.getFirstname());
		userOrder.setLastname(cartOrder.getLastname());
		userOrder.setAddress(cartOrder.getAddress());
		userOrder.setDistrict(cartOrder.getDistrict());
		userOrder.setState(cartOrder.getState());
		userOrder.setContact(cartOrder.getContact());
		userOrder.setPinCode(cartOrder.getPinCode());
		
		DateFormat df = DateFormat.getDateInstance();
		Calendar cl = Calendar.getInstance();
		String orderDate = df.format(cl.getTime());
		userOrder.setDate(orderDate);
		//userOrder.setDate(LocalDate.now().toString());
		userOrder.setStatus(OrderStatus.valueOf("PLACED"));
		userOrder.setPaidAmount(cartOrder.getPaidAmount());
		userOrder.setPaymentMode(cartOrder.getPaymentMode());
		
		Set<CartItem> cartItems  = cartOrder.getCartItems();
		// Add each cart item and its associated quantities to the order
	    for (CartItem cartItem : cartItems) {
	    	Optional<CartItem> checkItem = cartRepo.findById(cartItem.getId());
	    	if(checkItem.isPresent()) {
	    		CartItem existingCartItem = checkItem.get();
	    		
	    		for (ProductQuantity productQuantity : existingCartItem.getProductQuantities()) {
	    	        productQuantity.setCartItem(null);
	    	    }
	    		for (ComboProductQuantity comboProductQuantity : existingCartItem.getComboProductQuantities()) {
	                comboProductQuantity.setCartItem(null);
	            }
	    		
	    		if(checkItem.get().getProductQuantities().size() > 0) {	    			
	    			userOrder.getProducts().addAll(checkItem.get().getProductQuantities());
	    		}
	    		else if (checkItem.get().getComboProductQuantities().size() > 0) {
	    			userOrder.getComboProducts().addAll(checkItem.get().getComboProductQuantities());
	    		}
	    		cartRepo.delete(checkItem.get());
	    	}else {
	    		Map<String, Object> body = new LinkedHashMap<>();
 	            body.put("timestamp", LocalDateTime.now());
 	            body.put("message", "No such cart item with id: "+cartItem.getId().toString()+" to make an order");
 	            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    	}
	    }
		
//		userOrder.setConfirmationEmailSent(true);
//		UserOrder orderCreated = this.userOrderService.saveOrder(userOrder);
//		return ResponseEntity.ok(orderCreated);
		// Save the user order
	    UserOrder orderCreated = userOrderService.saveOrder(userOrder);
	    
	    if(orderCreated.getUsername().equals(userByPricipal.getUsername()) &&
	       foundUser.getReferredByCode() != null && !foundUser.getReferredByCode().isEmpty()
	       && !foundUser.isReferredVerified()) {
	        foundUser.setReferredVerified(true);
	        userRepo.save(foundUser);
	    }

//	    cartItems.forEach(cartItem -> cartService.removeCartItemById(cartItem.getId()));
//	    
//	    List<OrderInvoiceDto> productDTOs = new ArrayList<>();
//	    for (ProductQuantity productQuantity : orderCreated.getProducts()) {
//	        Product product = productQuantity.getProduct();
//	        byte[] imageData = product.getMainImage().getImageData();
//	        String base64Image = Base64.getEncoder().encodeToString(imageData);
//	        
////	        System.out.println("\nbase64Image string: \n"+base64Image+"\n");
//	        
//	        OrderInvoiceDto productDTO = new OrderInvoiceDto();
//	        productDTO.setProduct(product);
//	        productDTO.setQuantity(productQuantity.getQuantity());
//	        productDTO.setBase64Image(base64Image);
//	        
//	        productDTOs.add(productDTO);
//	    }
//
//	    // Prepare template variables for email
//	    Map<String, Object> templateVariables = new HashMap<>();
//	    templateVariables.put("products", productDTOs);
//	    
//	    Long CartItemId = cartOrder.getCartItems().stream().findAny().get().getid();
//	    
//	    CartItem cartItem = cartRepo.findById(CartItemId).orElse(null);
//	    
//	    String recipientEmail = null;
//	    
//	    if(cartItem == null) {
//	    	throw new UserNotFoundException("Cart item by the provided cart order item not found.");
//	    }
//	    else{
//	    	recipientEmail = cartItem.getUser().getEmail();
//	    }
//
//	    // Send order invoice email
//	    try {
//	        emailUtil.sendOrderInvoiceEmail(recipientEmail, "Order Invoice", "email-template", templateVariables);
//	    } catch (MessagingException e) {
//	        // Handle exception
//	        e.printStackTrace();
//	        // You might want to return an error response in case of email sending failure
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send order invoice email");
//	    }

	    return ResponseEntity.ok(orderCreated);
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
