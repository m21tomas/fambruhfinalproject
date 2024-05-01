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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import coms.model.product.Product;
import coms.model.product.ComboProduct;
import coms.service.ProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
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
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,@Valid @RequestBody Product product){
		return productService.updateProductData(id, product);
	}
	
	//find product by id
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
		return productService.findProductById(id);
	}

	//find all products
	@GetMapping("/get/all")
	public ResponseEntity<?> getAllProducts(){
		return productService.findAllProducts();
	}

    // Delete product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProductById(id);
    }
    
    // Set product availability
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/set-availability/{id}")
    public ResponseEntity<?> setAvailability(@PathVariable("id") Long id, @RequestParam boolean status){
        return productService.changeProductAvailability(id, status);
    }
    
    // Get available products
    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getAvailable(@PathVariable("name") String name){
        return productService.findTrueProduct(name);
    }




 

}

