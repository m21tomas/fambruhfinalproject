package coms.model.cartorder;

import coms.model.product.ProductSize;

public class CartItem {
	private Long pid;
    private String name;
    private String brand;
    private Double price;
    private String img3;
    private Integer quantity;
    private ProductSize selectedSize; // Add selectedSize property
    
    public CartItem() {}
    
	public CartItem(Long pid, String name, String brand, Double price, String img3, Integer quantity,
			ProductSize selectedSize) {
		super();
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.img3 = img3;
		this.quantity = quantity;
		this.selectedSize = selectedSize;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProductSize getSelectedSize() {
		return selectedSize;
	}

	public void setSelectedSize(ProductSize selectedSize) {
		this.selectedSize = selectedSize;
	}
}
