package coms.model.dtos;

import java.util.Set;

import coms.model.product.ProductSize;

public class ProductResponseDto {
	private Long pid;
    private String name;
    private String brand;
    private String description;
    private String salt;
    private int totalAvailable;
    private Double price;
    private Double discountedPrice;
    private boolean available;
    private Set<ProductSize> sizes;
    private byte[] mainImage;
    private byte[] hoverImage;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;
    private byte[] detailImage;
    
    public ProductResponseDto() {}
    
	public ProductResponseDto(Long pid, String name, String brand, String description, String salt, int totalAvailable,
			Double price, Double discountedPrice, boolean available, Set<ProductSize> sizes,
			byte[] mainImageData, byte[] hoverImage, byte[] image1, byte[] image2, byte[] image3,
			byte[] detailImage) {
		super();
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.salt = salt;
		this.totalAvailable = totalAvailable;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.available = available;
		this.sizes = sizes;
		this.mainImage = mainImageData;
		this.hoverImage = hoverImage;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
		this.detailImage = detailImage;
	}
	
	public Long getPid() {
		return pid;
	}
	
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public int getTotalAvailable() {
		return totalAvailable;
	}
	
	public void setTotalAvailable(int totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Set<ProductSize> getSizes() {
		return sizes;
	}
	
	public void setSizes(Set<ProductSize> sizes) {
		this.sizes = sizes;
	}
	
	public byte[] getMainImage() {
		return mainImage;
	}
	
	public void setMainImage(byte[] mainImage) {
		this.mainImage = mainImage;
	}

	public byte[] getHoverImage() {
		return hoverImage;
	}

	public void setHoverImage(byte[] hoverImage) {
		this.hoverImage = hoverImage;
	}

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

	public byte[] getImage3() {
		return image3;
	}

	public void setImage3(byte[] image3) {
		this.image3 = image3;
	}

	public byte[] getDetailImage() {
		return detailImage;
	}

	public void setDetailImage(byte[] detailImage) {
		this.detailImage = detailImage;
	}
	
}
