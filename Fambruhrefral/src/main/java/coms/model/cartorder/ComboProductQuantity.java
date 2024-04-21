package coms.model.cartorder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import coms.model.product.ComboProduct;

@Entity
public class ComboProductQuantity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private CartItem cartItem;

	@ManyToOne
	private ComboProduct comboProduct;

	private int quantity;
	
	public ComboProductQuantity() {}
	
	public ComboProductQuantity(ComboProduct comboProduct, int quantity) {
		super();
		this.comboProduct = comboProduct;
		this.quantity = quantity;
	}

	public ComboProductQuantity(CartItem cartItem, ComboProduct comboProduct, int quantity) {
		super();
		this.cartItem = cartItem;
		this.comboProduct = comboProduct;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	public ComboProduct getComboProduct() {
		return comboProduct;
	}

	public void setComboProduct(ComboProduct comboProduct) {
		this.comboProduct = comboProduct;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
