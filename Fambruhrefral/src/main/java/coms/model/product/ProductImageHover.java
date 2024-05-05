package coms.model.product;

import java.io.File;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "HOVER_IMAGE")
public class ProductImageHover {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imgId;
    
    private String name;
    
    private String type;
    
    private String filePath;
    
    @Column(length = 500)
    private String externalUrl;
    
//    @Lob
//    @Column(name = "image_data")
//    private byte[] imageData;
    
    @OneToOne(mappedBy = "hoverImage")
    @JsonBackReference
    private Product product;

	public ProductImageHover() {
		super();
	}

	public ProductImageHover(Long imgId, String name, String type, String filePath, Product product, String externalUrl) {
		super();
		this.imgId = imgId;
		this.name = name;
		this.type = type;
		this.filePath = filePath;
		this.product = product;
		this.externalUrl = externalUrl;
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

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

    
   
}
