package coms.model.product;

import java.io.File;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "MAIN_IMAGE")
public class ProductImageMain {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imgId;
    
    private String name;
    
    private String type;
    
    private String filePath;
    
    @OneToOne(mappedBy = "mainImage")
    @JsonBackReference
    private Product product;

	public ProductImageMain() {
		super();
	}

	public ProductImageMain(Long imgId, String name, String type, String filePath, Product product) {
		super();
		this.imgId = imgId;
		this.name = name;
		this.type = type;
		this.filePath = filePath;
		this.product = product;
			}

	@PreRemove
    public void preRemove() {
        // Delete the associated file from the filesystem
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.err.println("\n"+filePath + " deletion failed!\n");
                }
            }
        }
    }
	
	public Long getImgId() {
		return imgId;
	}

	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
 
    // Builder pattern implementation
    public static class Builder {
        private Long imgId;
        private String name;
        private String type;
        private String filePath;
        private Product product;
        
        public Builder imgId(Long imgId) {
            this.imgId = imgId;
            return this;
        }
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }
        
        public Builder product(Product product) {
            this.product = product;
            return this;
        }
        
        public ProductImageMain build() {
            return new ProductImageMain(imgId, name, type, filePath, product);
        }
    }
    
    public static Builder builder() {
        return new Builder();
    }


}
