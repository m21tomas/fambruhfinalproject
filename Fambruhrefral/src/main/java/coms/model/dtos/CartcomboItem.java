package coms.model.dtos;

import coms.model.product.ProductSize;

public class CartcomboItem {
	private String comboId;
    private Long pid1;
    private Long pid2;
    private String name;
    private Double price;
    private Integer quantity;
    private ProductSize selectedSize1;
    private ProductSize selectedSize2;
    private String img3;
    
    public CartcomboItem() {}
    
	public CartcomboItem(String comboId, Long pid1, Long pid2, String name, Double price, Integer quantity,
			ProductSize selectedSize1, ProductSize selectedSize2, String img3) {
		super();
		this.comboId = comboId;
		this.pid1 = pid1;
		this.pid2 = pid2;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.selectedSize1 = selectedSize1;
		this.selectedSize2 = selectedSize2;
		this.img3 = img3;
	}

	public String getComboId() {
		return comboId;
	}

	public void setComboId(String comboId) {
		this.comboId = comboId;
	}

	public Long getPid1() {
		return pid1;
	}

	public void setPid1(Long pid1) {
		this.pid1 = pid1;
	}

	public Long getPid2() {
		return pid2;
	}

	public void setPid2(Long pid2) {
		this.pid2 = pid2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ProductSize getSelectedSize1() {
		return selectedSize1;
	}

	public void setSelectedSize1(ProductSize selectedSize1) {
		this.selectedSize1 = selectedSize1;
	}

	public ProductSize getSelectedSize2() {
		return selectedSize2;
	}

	public void setSelectedSize2(ProductSize selectedSize2) {
		this.selectedSize2 = selectedSize2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}
}
