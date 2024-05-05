package coms.service;

import java.io.FileInputStream;
import java.io.IOException;
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
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import coms.model.cartorder.CartItem;
import coms.model.cartorder.CartItemBack;
import coms.model.cartorder.CartOrder;
import coms.model.cartorder.CartOrderBack;
import coms.model.cartorder.ComboProductQuantity;
import coms.model.cartorder.UserOrder;
import coms.model.dtos.CartcomboItem;
import coms.model.dtos.OrderInvoiceDto;
import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.product.ProductQuantity;
import coms.model.product.ProductSize;
import coms.model.user.User;
import coms.repository.CartRepository;
import coms.repository.ComboProductRepository;
import coms.repository.ComboQuantityRepo;
import coms.repository.OrderRepo;
import coms.repository.OrderStatus;
import coms.repository.ProductQuantityRepo;
import coms.repository.ProductRepo;
import coms.repository.Size;
import coms.repository.UserRepo;

@Service
public class UserOrderService {
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private UserRepo userRepo;
	
	@Value("${refLevel1}")
    private int refLevel1;
	
	@Value("${refLevel2}")
    private int refLevel2;
	
	@Value("${refLevel3}")
    private int refLevel3;
	
	@Value("${refLevel4}")
    private int refLevel4;
	
	@Value("${refLevel5}")
    private int refLevel5;
	
	@Autowired
    private ProductRepo productRepo;
	
	@Autowired
	private ComboProductRepository comboRepo;

    @Autowired
    private OrderRepo orderRepo;
    
	@Autowired
	private CartRepository cartRepo;
	
    @Autowired
    private ProductQuantityRepo productQuantityRepo;
    
    @Autowired
    private ComboQuantityRepo comboQuantityRepo;
    
    @Autowired
	private EmailUtil emailUtil;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Transactional
    public ResponseEntity<?> saveOrder(CartOrder cartOrder, Principal principal) {
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
		userOrder.setStatus(OrderStatus.valueOf("PLACED"));
		userOrder.setPaidAmount(cartOrder.getPaidAmount());
		userOrder.setPaymentMode(cartOrder.getPaymentMode());
		
		Set<CartItem> cartItems = cartOrder.getCartItems();
		Set<CartcomboItem> cartcomboItems = cartOrder.getCartcomboItems();
		Set<ProductQuantity> productQuantities = new HashSet<>();
		Set<ComboProductQuantity> comboProductQuantities = new HashSet<>();
		
		if(cartcomboItems.size() > 0) {
			for(CartcomboItem item : cartcomboItems) {
				Optional<Product> optProd1 = productRepo.findById(item.getPid1());
				Optional<Product> optProd2 = productRepo.findById(item.getPid2());
				
				if(!optProd1.get().isAvailable()) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "Product1 is unavailable");
					return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
				}
				if(!optProd2.get().isAvailable()) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "Product2 is unavailable");
					return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
				}
				
				if(optProd1.isPresent() && optProd2.isPresent()) {
					Size size1 = item.getSelectedSize1().getSizeName();
					Size size2 = item.getSelectedSize2().getSizeName();

					if(optProd1.get().getSizes().stream().
							anyMatch(product -> product.getSizeId().equals(item.getSelectedSize1().getSizeId()))) {
						
						ProductSize dbProductSize = optProd1.get().getSizes().stream()
								.filter(theSize -> theSize.getSizeId().equals(item.getSelectedSize1().getSizeId()))
								.findFirst().get();
						
                        if(dbProductSize.getSizeName() != item.getSelectedSize1().getSizeName()){
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided combo product first product size name: "+item.getSelectedSize1().getSizeName()+" is not like in database: "+ dbProductSize.getSizeName());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(dbProductSize.isAvailable() != item.getSelectedSize1().isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided combo product first product size status: "+item.getSelectedSize1().isAvailable()+" is not like database product size availability status: "+ dbProductSize.isAvailable());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(!dbProductSize.isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "This combo product first product size is unavailable!");
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						}
						size1 = dbProductSize.getSizeName();
					}else {
						Map<String, Object> body = new LinkedHashMap<>();
						body.put("timestamp", LocalDateTime.now());
						body.put("message", "Combo product size with your provided size id: "+item.getSelectedSize1().getSizeId()+" for the first product is not found!");
						return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
					}
					
					if(optProd2.get().getSizes().stream()
							.anyMatch(product -> product.getSizeId().equals(item.getSelectedSize2().getSizeId()))) {
						
						ProductSize dbProductSize2 = optProd2.get().getSizes().stream()
								.filter(theSize -> theSize.getSizeId().equals(item.getSelectedSize2().getSizeId()))
								.findFirst().get();
						
						if(dbProductSize2.getSizeName() != item.getSelectedSize2().getSizeName()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided combo product second product size name: "+item.getSelectedSize2().getSizeName()+" is not like in database: "+ dbProductSize2.getSizeName());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(dbProductSize2.isAvailable() != item.getSelectedSize2().isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided combo product second product size status: "+item.getSelectedSize2().isAvailable()+" is not like database second product size availability status: "+ dbProductSize2.isAvailable());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(!dbProductSize2.isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "This combo product second product size is unavailable!");
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						}
						size2 = dbProductSize2.getSizeName();
					}else {
						Map<String, Object> body = new LinkedHashMap<>();
						body.put("timestamp", LocalDateTime.now());
						body.put("message", "Combo product size with your provided size id: "+item.getSelectedSize2().getSizeId()+" for the second product is not found!");
						return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
					}
					
					ComboProduct comboProduct = new ComboProduct(optProd1.get(), optProd2.get(), size1, size2);
					
					ComboProduct savedCombo = comboRepo.save(comboProduct);
					
					try {
						System.out.println("COMBO PRODUCT: "+objectMapper.writeValueAsString(savedCombo));
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ComboProductQuantity comboProductQuantity = new ComboProductQuantity(savedCombo, item.getQuantity());
					
					comboProductQuantity.setCartItem(null);
					
					saveComboProductQuantity(comboProductQuantity);
					
					comboProductQuantities.add(comboProductQuantity);
				}else if(!optProd1.isPresent()){
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "In your CartcomboItem the product with id: "+item.getPid1()+" cannot be found in the database.");
					body.put("message2", "You have to add both exisitng products.");
					return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
				}else if(!optProd2.isPresent()){
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "In your CartcomboItem the product with id: "+item.getPid2()+" cannot be found in the database.");
					body.put("message2", "You have to add both exisitng products.");
					return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
				}
			}
			userOrder.setComboProducts(comboProductQuantities);
		}
		
		if(cartItems.size() > 0) {
			for(CartItem item : cartItems) {
				Optional<Product> optProduct0 = productRepo.findById(item.getPid());
				Optional<Product> productAvailable = productRepo.findByNameAndAvailableTrue(item.getName()).stream().findAny();
				
				System.out.println("Found product: "+ optProduct0.get().getName()+", id: "+optProduct0.get().getPid()+" by calling id: "+item.getPid());
				System.out.println("Found product: "+ productAvailable.get().getName()+", id: "+productAvailable.get().getPid()+" by calling name: "+item.getName());
				
				if(!optProduct0.get().getName().equals(item.getName())) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message1", "Provided product name: "+item.getName()+" is not like in database by the provided id product name: "+ optProduct0.get().getName());
					body.put("message2", "Both - the id and the name should correspond to the exisitng product");
					return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
				}
				if(!optProduct0.get().isAvailable()) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "Product is unavailable");
					return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
				}
				
				if(optProduct0.isPresent() && productAvailable.isPresent()) {
					Size size = item.getSelectedSize().getSizeName();
					
					//Optional<ProductSize> productSize = sizeRepo.findById(item.getSelectedSize().getSizeId());
					optProduct0.get().getSizes().stream().forEach(itemSize -> {System.out.println("Checking size id: "+itemSize.getSizeId()+" by calling size id: "+item.getSelectedSize().getSizeId()+", is equal: "+(itemSize.getSizeId().equals(item.getSelectedSize().getSizeId())));});
				    
					if(optProduct0.get().getSizes().stream()
							.anyMatch(product -> product.getSizeId().equals(item.getSelectedSize().getSizeId()))) {
						
						ProductSize dbProductSize2 = optProduct0.get().getSizes().stream()
								.filter(theSize -> theSize.getSizeId().equals(item.getSelectedSize().getSizeId()))
								.findFirst().get();
						
						if(dbProductSize2.getSizeName() != item.getSelectedSize().getSizeName()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided product size name: "+item.getSelectedSize().getSizeName()+" is not like in database: "+ dbProductSize2.getSizeName());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(dbProductSize2.isAvailable() != item.getSelectedSize().isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "Provided product size status: "+item.getSelectedSize().isAvailable()+" is not like database availability status: "+ dbProductSize2.isAvailable());
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						} else if(!dbProductSize2.isAvailable()) {
							Map<String, Object> body = new LinkedHashMap<>();
							body.put("timestamp", LocalDateTime.now());
							body.put("message", "This product size is unavailable!");
							return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
						}
						size = dbProductSize2.getSizeName();
					}else {
						Map<String, Object> body = new LinkedHashMap<>();
						body.put("timestamp", LocalDateTime.now());
						body.put("message", "Product size with your provided size id: "+item.getSelectedSize().getSizeId()+" is not found!");
						return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
					}
					ProductQuantity productQuantity = new ProductQuantity(productAvailable.get(), size, item.getQuantity());
					productQuantity.setCartItem(null);
					saveProductQuantity(productQuantity);
					productQuantities.add(productQuantity);
				}else if(!optProduct0.isPresent()) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "Product with your provided product id: "+item.getPid()+" is not found!");
					body.put("message2", "You have to use product id and its name to identify it in database");
					return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
				}
				else if(!productAvailable.isPresent()) {
					Map<String, Object> body = new LinkedHashMap<>();
					body.put("timestamp", LocalDateTime.now());
					body.put("message", "Product with your provided name: "+item.getName()+" is not found!");
					body.put("message2", "You have to use product id and its name to identify it in database");
					return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
				}
			}
			userOrder.setProducts(productQuantities);
		}
		
		userOrder.setConfirmationEmailSent(true);

		// Save the user order
	    UserOrder orderCreated = orderRepo.save(userOrder);
	    
	    if(orderCreated.getUsername().equals(userByPricipal.getUsername()) &&
	       foundUser.getReferredByCode() != null && !foundUser.getReferredByCode().isEmpty()
	       && !foundUser.isRefVerified()) {
	    	
	    	foundUser.setRefVerified(true);
	        
	        userRepo.save(foundUser);
	        
	        List<User> referalls = userRepo.findAllByReferralCode(foundUser.getReferredByCode());
	        
	        if(referalls.size()>1) {
	        	Map<String, Object> body = new LinkedHashMap<>();
				body.put("timestamp", LocalDateTime.now());
				body.put("message", "Critical error - There are more than one refferals with the same code!");
				return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	        } else if(referalls.size()==0){
	        	Map<String, Object> body = new LinkedHashMap<>();
				body.put("timestamp", LocalDateTime.now());
				body.put("message", "Critical error - There is no refferal with with the provided client reffered code!");
				return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	        }else {
	        	User referral = referalls.stream().findFirst().get();
	        	
	        	List<User> refUsers = userRepo.findAllByReferredByCode(referral.getReferralCode());
	        	
	        	if(refUsers.size() < refLevel1) referral.setRefLevel(0);
	        	else if(refUsers.size() >= refLevel1 && refUsers.size() < refLevel2) referral.setRefLevel(1);
	        	else if(refUsers.size() >= refLevel2 && refUsers.size() < refLevel3) referral.setRefLevel(2);
	        	else if(refUsers.size() >= refLevel3 && refUsers.size() < refLevel4) referral.setRefLevel(3);
	        	else if(refUsers.size() >= refLevel4 && refUsers.size() < refLevel5) referral.setRefLevel(4);
	        	else if(refUsers.size() >= refLevel5) referral.setRefLevel(5);
	        	
	        	referral.setCredits(referral.getCredits() + orderCreated.getPaidAmount()*(0.05 + 0.01*referral.getRefLevel()));
	        	
	        	userRepo.save(referral);
	        }
	    }

	    	List<OrderInvoiceDto> productDTOs = new ArrayList<>();
	    	for (ProductQuantity productQuantity : orderCreated.getProducts()) {
	    		Product product = productQuantity.getProduct();
	    		byte[] imageData = getImageFromFile(product.getHoverImage().getFilePath());
	    		String base64Image = Base64.getEncoder().encodeToString(imageData);
	    		
	    		OrderInvoiceDto productDTO = new OrderInvoiceDto();
	    		productDTO.setProduct(product);
	    		productDTO.setSize(productQuantity.getSize());
	    		productDTO.setQuantity(productQuantity.getQuantity());
	    		productDTO.setBase64Image(base64Image);
	    		productDTO.setContentId("attachment-"+product.getHoverImage().getName());
	    		productDTO.setExternalUrl(product.getHoverImage().getExternalUrl());
	    		productDTOs.add(productDTO);
	    	}
	    	productDTOs.stream().forEach(item -> System.out.println("Product name: "+item.getProduct().getName()));
	    	// Prepare template variables for email
	    	 Map<String, Object> templateVariables = new HashMap<>();
	    	 templateVariables.put("products", productDTOs);
	    	
	    	String recipientEmail = foundUser.getEmail();
	    	
	    	// Send order invoice email
	    	try {
	    		emailUtil.sendOrderInvoiceEmailWithBase64Images(recipientEmail, "Order Invoice", "email-template3", templateVariables);
	    		//emailUtil.sendOrderInvoiceEmailWithContentIdsImages(recipientEmail, "Order Invoice", "email-template3", templateVariables, productDTOs);
	    	} catch (MessagingException e) {
	    		// Handle exception
	    		e.printStackTrace();
	    		// You might want to return an error response in case of email sending failure
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send order invoice email");
	    	}
	    	
	    	return ResponseEntity.ok(orderCreated);
	}
    
    @Transactional
    public ResponseEntity<?> saveOrder2(CartOrderBack cartOrder, Principal principal) {
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
		
		Set<CartItemBack> cartItems  = cartOrder.getCartItems();
		// Add each cart item and its associated quantities to the order
	    for (CartItemBack cartItem : cartItems) {
	    	Optional<CartItemBack> checkItem = cartRepo.findById(cartItem.getId());
	    	if(checkItem.isPresent()) {
	    		CartItemBack existingCartItem = checkItem.get();
	    		
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
	    UserOrder orderCreated = orderRepo.save(userOrder);

	    if(orderCreated.getUsername().equals(userByPricipal.getUsername()) &&
	       foundUser.getReferredByCode() != null && !foundUser.getReferredByCode().isEmpty()
	       && !foundUser.isRefVerified()) {
	        foundUser.setRefVerified(false);
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
    
    private byte[] getImageFromFile(String filePath) {
	    try (FileInputStream fis = new FileInputStream(filePath)) {
	        return fis.readAllBytes();
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle file reading exception
	        return null;
	    }
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
