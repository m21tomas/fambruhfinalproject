package coms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import coms.model.dtos.ProductResponseDto;
import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.product.ProductImage1;
import coms.model.product.ProductImage2;
import coms.model.product.ProductImage3;
import coms.model.product.ProductImageDetail;
import coms.model.product.ProductImageHover;
import coms.model.product.ProductImageMain;
import coms.repository.CartRepository;

import coms.repository.MainImageRepo;
import coms.repository.ProductRepo;
import coms.repository.Sizerepo;
import coms.repository.UserRepo;
import coms.repository.WishlistRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productRepo;
    
    
    private String uploadPath = "C:\\Users\\Junaid Shaikh\\Downloads\\sizesadded\\fambruhfinalproject-main\\Fambruhrefral2\\images\\";
 // Assuming productDTO has a property for imageFileName


//    @Value("${$IMAGES_PATH}")
//    private String uploadPath;
    
 //   @Value("C:/Users/Junaid Shaikh/Desktop/images/")
 //   private String uploadPath;
    
  
    
    @Autowired
    private UserRepo userrepo;
    
    @Autowired
    private MainImageRepo imagerepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartRepository cartItemRepository;
    
    @Autowired
    private WishlistRepository wishlistrepo;
    
    @Autowired
    private Sizerepo sizerepo;

    // Add a product
    @Transactional
    public ResponseEntity<?> addFullProduct(String productData, MultipartFile mainImageFile,
            MultipartFile hoverImagefile, MultipartFile detailImagefile, MultipartFile Image1file,
            MultipartFile Image2file, MultipartFile Image3file) {

        // Check content types of all files to ensure they are images
        if (!isValidImage(mainImageFile) || !isValidImage(hoverImagefile) || !isValidImage(detailImagefile)
                || !isValidImage(Image1file) || !isValidImage(Image2file) || !isValidImage(Image3file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Only images, GIFs, and WebP images are allowed for the images.");
        }

        // Create instances for each image and transfer files to final paths
        ProductImageMain img1 = createProductImageMain(mainImageFile);
        ProductImageHover img2 = createProductImageHover(hoverImagefile);
        ProductImageDetail img3 = createProductImageDetail(detailImagefile);
        ProductImage1 img4 = createProductImage1(Image1file);
        ProductImage2 img5 = createProductImage2(Image2file);
        ProductImage3 img6 = createProductImage3(Image3file);

        // Populate product object with image instances
        Product product = null;
        try {
            product = objectMapper.readValue(productData, Product.class);
            product.setMainImage(img1);
            product.setHoverImage(img2);
            product.setDetailImage(img3);
            product.setImage1(img4);
            product.setImage2(img5);
            product.setImage3(img6);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
        }

        // Save the product
        Product addedProduct = productRepo.save(product);
        return ResponseEntity.ok(addedProduct);
    }
    
    private ProductImageMain createProductImageMain(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImageMain img = new ProductImageMain();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
    }
    
    private ProductImageHover createProductImageHover(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImageHover img = new ProductImageHover();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
	}
    
    private ProductImageDetail createProductImageDetail(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImageDetail img = new ProductImageDetail();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
	}
    
    private ProductImage1 createProductImage1(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImage1 img = new ProductImage1();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
	}
    
    private ProductImage2 createProductImage2(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImage2 img = new ProductImage2();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
	}

    private ProductImage3 createProductImage3(MultipartFile file) {
    	String fileName = file.getOriginalFilename();
        String filePath = getFinalUploadPath() + File.separator + fileName;
     // Replace backslashes with forward slashes
        filePath = filePath.replace("\\", "/");
        try {
            // Transfer file to the final upload path
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file transfer exception
        }
        ProductImage3 img = new ProductImage3();
        img.setName(fileName);
        img.setType(file.getContentType());
        img.setFilePath(filePath);
        return img;
	}

//    private boolean isValidImage1(MultipartFile file) {
//        String contentType = file.getContentType();
//        return contentType != null && (contentType.startsWith("image/") || contentType.equals("image/gif") || contentType.equals("image/webp"));
//    }
    private String getFinalUploadPath() {
    	// Remove any trailing slash if present
        if (uploadPath.endsWith("/") || uploadPath.endsWith("\\")) {
            uploadPath = uploadPath.substring(0, uploadPath.length() - 1);
        }
    	
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            try {
                // Create the directory if it does not exist
                if (directory.mkdirs()) {
                	System.out.println("\n Images upload path: " + uploadPath+"\n"); 
                    return uploadPath;
                }
            } catch (SecurityException e) {
                // Handle the exception (e.g., log an error message)
                System.err.println("Error creating directory: " + e.getMessage());
            }
        } else {
        	System.out.println("\n Images upload path: " + uploadPath+"\n"); 
            return uploadPath;
        }
		
        // Fallback to default directory
        String defaultDirectory = System.getProperty("user.dir") + "/src/main/resources/images";
        File defaultDir = new File(defaultDirectory);
        if (defaultDir.exists()) {
        	System.out.println("\nUsing default images directory: "+defaultDir.getAbsolutePath()+"\n");
            return defaultDir.getAbsolutePath();
        } else {
            System.err.println("Default directory 'images' not found.");
        }
        // Default fallback path (current directory)
        return ".";
    }

    // Helper method to validate image file format
    private boolean isValidImage(MultipartFile file) {
        if (file.getContentType() != null) {
            String contentType = file.getContentType();
            return contentType.startsWith("image/") || contentType.equals("image/gif") || contentType.equals("image/webp");
        }
        return false;
    }
    
    @Transactional
    public void addProduct(Product updateProduct) {
    	productRepo.save(updateProduct);
	}
    
    private ProductResponseDto productToDto (Product product) {
    	ProductResponseDto dto = new ProductResponseDto();
        dto.setPid(product.getPid());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setSalt(product.getSalt());
        dto.setTotalAvailable(product.getTotalAvailable());
        dto.setPrice(product.getPrice());
        dto.setDiscountedPrice(product.getDiscountedPrice());
        dto.setAvailable(product.isAvailable());
        dto.setSizes(product.getSizes());
        dto.setMainImage(getImageFromFile(product.getMainImage().getFilePath()));
        dto.setHoverImage(getImageFromFile(product.getHoverImage().getFilePath()));
        dto.setImage1(getImageFromFile(product.getImage1().getFilePath()));
        dto.setImage2(getImageFromFile(product.getImage2().getFilePath()));
        dto.setImage3(getImageFromFile(product.getImage3().getFilePath()));
        dto.setDetailImage(getImageFromFile(product.getDetailImage().getFilePath()));
        return dto;
    }
    
    // Find a product by ID
    public ResponseEntity<?> findProductById(Long pid) {
    	Product product = productRepo.findById(pid).orElse(null);
    	if(product != null) {
	    	return ResponseEntity.ok(productToDto(product));
    	}
    	else {
    		Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "Product with id="+pid+" is not found.");
    		return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
    	}
        
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
    
    // Find all products
    public ResponseEntity<?> findAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductResponseDto> allProductsDto = new ArrayList<>();
		allProducts.forEach(product -> {
	        allProductsDto.add(productToDto(product));
		});
		if(allProducts.isEmpty()) {
			Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "No any products.");
    		return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
		}else {
			return ResponseEntity.ok(allProductsDto);
		}
    }
    
    @Transactional
    public ResponseEntity<?> updateProductData(Long id, Product product){
    	Optional<Product> optionalProduct = productRepo.findById(id);
    	if(!optionalProduct.isPresent()) {
    		Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "Product with id=" + id + " is not found.");
            return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
    	} else {
    		Product updateProduct = new Product();
    		updateProduct.setName(product.getName());
    		updateProduct.setBrand(product.getBrand());
    		updateProduct.setDescription(product.getDescription());
    		updateProduct.setSalt(product.getSalt());
    		updateProduct.setTotalAvailable(product.getTotalAvailable());
    		updateProduct.setPrice(product.getPrice());
    		updateProduct.setDiscountedPrice(product.getDiscountedPrice());
    		updateProduct.setAvailable(product.isAvailable());
    		updateProduct.setSizes(product.getSizes());
    		
    		productRepo.save(updateProduct);
    		
    		return ResponseEntity.status(HttpStatus.OK).build();
    	}
    }
    
    @Transactional
    public ResponseEntity<?> changeProductAvailability(Long id, boolean status){
    	Optional<Product> optionalProduct = productRepo.findById(id);
    	if (optionalProduct.isPresent()) {
            Product updateProduct = optionalProduct.get();
            updateProduct.setAvailable(status);
            productRepo.save(updateProduct);
            return ResponseEntity.ok().build();
        } else {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "Product with id=" + id + " is not found.");
            return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
        }
    }
    
    // Find products by name or salt
    public List<Product> findByNameOrSalt(String name, String salt) {
        return this.productRepo.findByNameContainingIgnoreCaseOrSaltContainingIgnoreCase(name, salt);
    }
    
    @Transactional
    public void deleteCartItemsByProductId(Long productId) {
        cartItemRepository.deleteByProductQuantities_Product_Pid(productId);
    }

    @Transactional
    public void deleteCartItemsByComboProductId(Long comboProductId) {
        cartItemRepository.deleteByComboProductQuantities_ComboProduct_Id(comboProductId);
    }
    
    // Delete a product by ID
    @Transactional
    public ResponseEntity<?> deleteProductById(Long pid) {
    	Optional<Product> optionalProduct = productRepo.findById(pid);
    	if (optionalProduct.isPresent()) {
    		deleteCartItemsByProductId(pid);
            productRepo.deleteById(pid);
            Optional<Product> deletedProduct = productRepo.findById(pid);
            if (!deletedProduct.isPresent()) {            	
            	return ResponseEntity.ok().build();
            }
            else {
            	return ResponseEntity.internalServerError().build();
            }
        } else {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "Product with id=" + pid + " is not found.");
            return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
        }
    }
    
    // Find available products by name
    public ResponseEntity<?> findTrueProduct(String name) {
        List<Product> products = productRepo.findByNameAndAvailableTrue(name);
        if(products.isEmpty()) {
        	Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", "No any available products found.");
    		return new ResponseEntity<Map<String, Object>>(body, HttpStatus.NOT_FOUND);
        } else {
        	List<ProductResponseDto> allProductsDto = new ArrayList<>();
        	products.forEach(product -> {
    	        allProductsDto.add(productToDto(product));
    		});
        	 return ResponseEntity.ok(allProductsDto);
        }
    }

    // Combo Product Services

    // Find a product by ID
    public Product findProduct(Long pid) {
        return this.productRepo.findById(pid).orElse(null);
    }


    
}
