package coms.controller;
import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coms.model.cartorder.Wishlist;
import coms.model.dtos.CartItemResponseDto;
import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.repository.Size;
import coms.service.Cartwishservice;


@RestController
@CrossOrigin(origins = "*")
public class CartwishController {
    @Autowired
    private Cartwishservice cartWishService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/cart")
    public ResponseEntity<List<CartItemResponseDto>> getAllCartItems(@RequestParam String username) {
        List<CartItemResponseDto> cartItems = cartWishService.getAllCartItemsByUsername(username);
        return ResponseEntity.ok(cartItems);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/wishlist")
    public ResponseEntity<List<Wishlist>> getAllWishlistItems(@RequestParam String username) {
        List<Wishlist> wishlistItems = cartWishService.getAllWishlistItemsByUsername(username);
        return ResponseEntity.ok(wishlistItems);
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Product product, @RequestParam String size, @RequestParam int quantity, @RequestParam String username) {
        System.out.println("\nAdding product to cart - USERNAME: "+username+"\n");
        return cartWishService.addToCart(product, size, quantity, username);
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/cart/addCombo")
    public ResponseEntity<?> addComboToCart(@RequestBody ComboProduct comboProduct, @RequestParam int quantity, @RequestParam String username) {
        System.out.println("\nAdding combo product to cart - USERNAME: "+username+"\n");
        return cartWishService.addComboToCart(comboProduct, quantity, username);
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wishlist/add")
    public ResponseEntity<?> addToWishlist(@RequestBody Product product, @RequestParam String size, @RequestParam String username) {
        cartWishService.addToWishlist(product, username, size);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/cart/move/{cartItemId}")
    public ResponseEntity<?> moveCartItemToWishlist(@PathVariable Long cartItemId, @RequestParam String username) {
        cartWishService.moveCartItemToWishlist(cartItemId, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wishlist/move/{wishlistItemId}")
    public ResponseEntity<?> moveWishlistItemToCart(@PathVariable int wishlistItemId, @RequestParam String username) {
        cartWishService.moveWishlistItemToCart(wishlistItemId, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @DeleteMapping("/cart/delete/{cartItemId}")
    public ResponseEntity<?> removeCartItemById(@PathVariable Long cartItemId) {
        return cartWishService.removeCartItemById(cartItemId);
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/wishlist/{wishlistItemId}")
    public ResponseEntity<?> removeWishlistItemById(@PathVariable int wishlistItemId) {
        cartWishService.removeWishlistItemById(wishlistItemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/cart/update/{cartItemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        cartWishService.updateCartItemQuantity(cartItemId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    // Additional method to update the size of a cart item
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/cart/update/size/{cartItemId}")
    public ResponseEntity<?> updateCartItemSize(@PathVariable Long cartItemId, @RequestParam Size size) {
        cartWishService.updateCartItemSize(cartItemId, size);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}