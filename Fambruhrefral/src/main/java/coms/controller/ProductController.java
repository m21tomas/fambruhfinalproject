package coms.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import coms.model.product.Product;
import coms.model.product.ComboProduct;
import coms.service.ProductService;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add/product")
    public ResponseEntity<?> addNewProduct(@RequestParam("product") String productData,
                                           @RequestParam("mainImage") MultipartFile mainImageFile,
                                           @RequestParam("hoverImage") MultipartFile hoverImagefile,
                                           @RequestParam("detailImage") MultipartFile detailImagefile,
                                           @RequestParam("image1") MultipartFile Image1file,
                                           @RequestParam("image2") MultipartFile Image2file,
                                           @RequestParam("image3") MultipartFile Image3file) throws IOException {
        return productService.addFullProduct(productData, mainImageFile, hoverImagefile, detailImagefile, Image1file, Image2file, Image3file);
    }

	//update existing product
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@Valid @RequestBody Product product){
		return productService.updateProductData(id, product);
	}
	
	//find product by id
	@GetMapping("get-product/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
		return productService.findProductById(id);
	}

	//find all products
	@GetMapping("/get/all-products")
	public ResponseEntity<?> getAllProducts(){
		return productService.findAllProducts();
	}

    // Delete product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProductById(id);
    }
    
    // Set product availability
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/set-availability/product/{id}")
    public ResponseEntity<?> setAvailability(@PathVariable("id") Long id, @RequestParam boolean status){
        return productService.changeProductAvailability(id, status);
    }
    
    // Get available products
    @GetMapping("/get/{name}")
    public ResponseEntity<?> getAvailable(@PathVariable("name") String name){
        return productService.findTrueProduct(name);
    }

    // Add new combo product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add/comboproduct")
    public ResponseEntity<?> addNewComboProduct(@RequestBody ComboProduct comboProduct) {
        ComboProduct savedComboProduct = productService.addComboProduct(comboProduct);
        return ResponseEntity.ok(savedComboProduct);
    }

    // Update existing combo product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/comboproduct/{id}")
    public ResponseEntity<?> updateComboProduct(@PathVariable("id") Long id, @Valid @RequestBody ComboProduct comboProduct) {
        ComboProduct existingComboProduct = productService.findComboProduct(id);
        if (existingComboProduct != null) {
            existingComboProduct.setProduct1(comboProduct.getProduct1());
            existingComboProduct.setProduct2(comboProduct.getProduct2());
            productService.addComboProduct(existingComboProduct);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get combo product by ID
    @GetMapping("/get/comboproduct/{id}")
    public ResponseEntity<?> getComboProductById(@PathVariable("id") Long id) {
        ComboProduct comboProduct = productService.findComboProduct(id);
        if (comboProduct != null) {
            return ResponseEntity.ok(comboProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all combo products
    @GetMapping("/get/all-comboproducts")
    public ResponseEntity<?> getAllComboProducts() {
        List<ComboProduct> comboProducts = productService.findAllComboProducts();
        return ResponseEntity.ok(comboProducts);
    }

    // Delete combo product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/comboproduct/{id}")
    public ResponseEntity<?> deleteComboProduct(@PathVariable("id") Long id) {
        productService.deleteComboProductById(id);
        return ResponseEntity.ok().build();
    }
}

