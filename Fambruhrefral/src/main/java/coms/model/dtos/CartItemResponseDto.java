package coms.model.dtos;

import coms.model.product.Product;
import coms.model.product.ComboProduct;
import coms.repository.Size;

public class CartItemResponseDto {

	private Long id;
	private String username;
	private Product product;
	private int quantity;
	private ComboProduct comboproduct;
	private Size size;

	public CartItemResponseDto() {}

	public CartItemResponseDto(Long id, String username, Product product, coms.model.product.ComboProduct comboproduct,
			int quantity, Size size) {
		super();
		this.id = id;
		this.username = username;
		this.product = product;
		this.quantity = quantity;
		this.comboproduct = comboproduct;
		this.size = size;
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

	public ComboProduct getComboproduct() {
		return comboproduct;
	}

	public void setComboproduct(ComboProduct comboproduct) {
		this.comboproduct = comboproduct;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

}