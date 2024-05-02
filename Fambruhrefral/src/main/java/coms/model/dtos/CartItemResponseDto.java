package coms.model.dtos;

import java.util.Set;

import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.product.ProductSize;

public class CartItemResponseDto {

	private Long id;
	private String username;
	private Product product;
	private int quantity;
	private int comboQuantity;
	private ComboProduct comboproduct;
	private Set<ProductSize> sizes;

	public CartItemResponseDto() {}

	public CartItemResponseDto(Long id, String username, Product product, coms.model.product.ComboProduct comboproduct,
			int quantity, int comboQuantity, Set<ProductSize> size) {
		super();
		this.id = id;
		this.username = username;
		this.product = product;
		this.quantity = quantity;
		this.comboQuantity = comboQuantity;
		this.comboproduct = comboproduct;
		this.sizes = size;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getComboQuantity() {
		return comboQuantity;
	}

	public void setComboQuantity(int comboQuantity) {
		this.comboQuantity = comboQuantity;
	}

	public ComboProduct getComboproduct() {
		return comboproduct;
	}

	public void setComboproduct(ComboProduct comboproduct) {
		this.comboproduct = comboproduct;
	}

	public Set<ProductSize> getSizes() {
		return sizes;
	}

	public void setSizes(Set<ProductSize> sizes) {
		this.sizes = sizes;
	}

}