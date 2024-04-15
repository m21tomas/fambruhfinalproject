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
    private Double productDiscountedPrice;
    private boolean isAvailable;
    private Set<ProductSize> sizes;
    private byte[] mainImageData;
    private byte[] hoverImageData;
    private byte[] image1Data;
    private byte[] image2Data;
    private byte[] image3Data;
    private byte[] detailImageData;
    
    public ProductResponseDto() {}
    
	public ProductResponseDto(Long pid, String name, String brand, String description, String salt, int totalAvailable,
			Double price, Double productDiscountedPrice, boolean isAvailable, Set<ProductSize> sizes,
			byte[] mainImageData, byte[] hoverImageData, byte[] image1Data, byte[] image2Data, byte[] image3Data,
			byte[] detailImageData) {
		super();
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.description = description;
		this.salt = salt;
		this.totalAvailable = totalAvailable;
		this.price = price;
		this.productDiscountedPrice = productDiscountedPrice;
		this.isAvailable = isAvailable;
		this.sizes = sizes;
		this.mainImageData = mainImageData;
		this.hoverImageData = hoverImageData;
		this.image1Data = image1Data;
		this.image2Data = image2Data;
		this.image3Data = image3Data;
		this.detailImageData = detailImageData;
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
	public Double getProductDiscountedPrice() {
		return productDiscountedPrice;
	}
	public void setProductDiscountedPrice(Double productDiscountedPrice) {
		this.productDiscountedPrice = productDiscountedPrice;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Set<ProductSize> getSizes() {
		return sizes;
	}
	public void setSizes(Set<ProductSize> sizes) {
		this.sizes = sizes;
	}
	public byte[] getMainImageData() {
		return mainImageData;
	}
	public void setMainImageData(byte[] mainImageData) {
		this.mainImageData = mainImageData;
	}
	public byte[] getHoverImageData() {
		return hoverImageData;
	}
	public void setHoverImageData(byte[] hoverImageData) {
		this.hoverImageData = hoverImageData;
	}
	public byte[] getImage1Data() {
		return image1Data;
	}
	public void setImage1Data(byte[] image1Data) {
		this.image1Data = image1Data;
	}
	public byte[] getImage2Data() {
		return image2Data;
	}
	public void setImage2Data(byte[] image2Data) {
		this.image2Data = image2Data;
	}
	public byte[] getImage3Data() {
		return image3Data;
	}
	public void setImage3Data(byte[] image3Data) {
		this.image3Data = image3Data;
	}
	public byte[] getDetailImageData() {
		return detailImageData;
	}
	public void setDetailImageData(byte[] detailImageData) {
		this.detailImageData = detailImageData;
	}
}
