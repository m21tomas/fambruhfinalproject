package coms.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import coms.model.cartorder.CartItemBack;
import coms.model.cartorder.ComboProductQuantity;
import coms.model.cartorder.Wishlist;
import coms.model.dtos.CartItemResponseDto;
import coms.model.product.Product;
import coms.model.product.ProductQuantity;
import coms.model.product.ComboProduct;
import coms.repository.*;
import coms.model.user.User;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class Cartwishservice {
    @Autowired
    private CartRepository cartItemRepository;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepo userRepository;

  
    // Method to retrieve all cart items by user's username
    public List<CartItemResponseDto> getAllCartItemsByUsername(String username) {
        return remapCartItemToDTO(cartItemRepository.findByUserUsername(username));
    }
    
    private static List<CartItemResponseDto> remapCartItemToDTO(List<CartItemBack> cartItem){
    	List<CartItemResponseDto> responseDto = new ArrayList<>();
    	
    	for(CartItemBack item : cartItem) {
    		CartItemResponseDto dtoItem = new CartItemResponseDto();
    		dtoItem.setId(item.getId());
    		dtoItem.setUsername(item.getUser().getUsername());
    		dtoItem.setProduct(item.getProductQuantities().stream()
    				.findAny().get().getProduct());
    		dtoItem.setQuantity(item.getProductQuantities().stream()
    				.findAny().get().getQuantity());
    		dtoItem.setComboproduct(item.getComboProductQuantities().stream()
    				.findAny().get().getComboProduct());
    		dtoItem.setComboQuantity(item.getComboProductQuantities().stream()
    				.findAny().get().getQuantity());
    		dtoItem.setSizes(item.getProductQuantities().stream()
    				.findAny().get().getProduct().getSizes());
    		
    		responseDto.add(dtoItem);
    	}
    	
    	return responseDto;
    }

    // Method to retrieve all wishlist items by user's username
    public List<Wishlist> getAllWishlistItemsByUsername(String username) {
        return wishlistRepository.findByUserUsername(username);
    }


  
    public List<CartItemResponseDto> getAllCartItemsByUsername(User username) {
        return remapCartItemToDTO(cartItemRepository.findByUser(username));
    }

    // Method to remove a cart item by its ID
    @Transactional
    public ResponseEntity<?> removeCartItemById(Long cartItemId) {
    	Optional<CartItemBack> cartItem = cartItemRepository.findById(cartItemId);
    	
    	if(cartItem.isPresent()) {
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		boolean isAdmin = authentication.getAuthorities().stream().anyMatch(item -> item.getAuthority().equals("ADMIN"));
    		
    		if(cartItem.get().getUser().getUsername().equals(authentication.getName()) && !isAdmin || isAdmin){    			
    			cartItemRepository.deleteById(cartItemId);
    			return new ResponseEntity<String>("CartItem deleted.", HttpStatus.OK);
    		} else {
    			Map<String, Object> body = new LinkedHashMap<>();
                body.put("timestamp", LocalDateTime.now());
                body.put("message", "You are not authorized to delete another user cart item.");
        		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    		}
    	}else {
    		Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "No cart item found by the provided cart item id.");
    		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    	}
    	
        
    }

    // Method to remove a wishlist item by its ID
    public void removeWishlistItemById(int wishlistItemId) {
        wishlistRepository.deleteById(wishlistItemId);
    }
    // Method to add a product to the wishlist
    public void addToWishlist(Product product, String size, String username) {
        User user = userRepository.findByUsername(username);
        Wishlist wishlistItem = new Wishlist(user, product, Size.valueOf(size));
        wishlistRepository.save(wishlistItem);
    }

    // Method to add a product to the cart
    public ResponseEntity<?> addToCart(Product product, String size, int quantity, String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
        	String authenticatedUsername = authentication.getName(); // Get the username from the principal
        	if (authenticatedUsername.equals(username)) { // Compare the extracted username to the provided one
	        	User user = userRepository.findByUsername(username);
	        	Optional<Product> foundProduct = productRepo.findById(product.getPid());
	        	if(foundProduct.isPresent()) {
	        		if(foundProduct.get().getSizes().stream().anyMatch(item -> item.getSizeName().toString().equals(size) && item.isAvailable())) {	        			
	        			CartItemBack cartItem = new CartItemBack();
	        			cartItem.setUser(user);
	        			ProductQuantity pq = new ProductQuantity(cartItem, foundProduct.get(), Size.valueOf(size), quantity);
	        			cartItem.getProductQuantities().add(pq);
	        			cartItemRepository.save(cartItem);
	        			return new ResponseEntity<>("Product added to cart", HttpStatus.CREATED);
	        		} else {
	        			Map<String, Object> body = new LinkedHashMap<>();
			            body.put("timestamp", LocalDateTime.now());
			            body.put("message", "Provided size for the product is unavailable.");
			            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	        		}
	        	}
	        	else {
	        		Map<String, Object> body = new LinkedHashMap<>();
		            body.put("timestamp", LocalDateTime.now());
		            body.put("message", "There is no such product that you are trying to add to cart.");
		            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	        	}
        	}else {
	            // Provide a more specific message indicating the username mismatch
	            Map<String, Object> body = new LinkedHashMap<>();
	            body.put("timestamp", LocalDateTime.now());
	            body.put("message", "Provided username doesn't match authenticated principal name.");
	            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
            }
        }
        else {
        	Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "Unauthorized request. You are not logged in.");
    		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
    }
    
  
    // Method to move a cart item to the wishlist
    public void moveCartItemToWishlist(Long cartItemId, String username) {
        CartItemBack cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            Product product = cartItem.getProductQuantities().stream().findAny().get().getProduct();
            Size productSize = cartItem.getProductQuantities().stream().findAny().get().getSize();
            removeCartItemById(cartItemId);
            addToWishlist(product, productSize.toString(), username);
        }
    }
    // Method to move a wishlist item to the cart

    public void moveWishlistItemToCart(int wishlistItemId, String username) {
        Wishlist wishlistItem = wishlistRepository.findById(wishlistItemId).orElse(null);
        if (wishlistItem != null) {
            Product product = wishlistItem.getProduct();
            Size size = wishlistItem.getProductSize();
            removeWishlistItemById(wishlistItemId);
            addToCart(product, size.toString(), 1, username);
        }
    }

    
 // Method to update the quantity of a cart item by product ID

    // Method to update the quantity of a cart item by product ID

    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItemBack cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
        	cartItem.getProductQuantities().stream().findAny().get().setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }
    // Method to update the size of a cart item
    public void updateCartItemSize(Long cartItemId, Size size) {
        CartItemBack cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
          
            cartItemRepository.save(cartItem);
        }
    }
    // Other methods...
}