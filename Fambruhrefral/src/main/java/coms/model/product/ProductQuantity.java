package coms.model.product;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import coms.model.cartorder.CartItem;
import coms.repository.Size;

@Entity
public class ProductQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pqid;
    
    @ManyToOne
    private CartItem cartItem;

    @ManyToOne
    @JsonIgnore
    private Product product;
    
    @Enumerated(EnumType.STRING)
    private Size size;

    private int quantity;

    public ProductQuantity() {}

    public ProductQuantity(Product product, Size size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public ProductQuantity(CartItem cartItem, Product product, Size size, int quantity) {
		this.cartItem = cartItem;
		this.product = product;
		this.size = size;
		this.quantity = quantity;
	}

	public Long getPqid() {
        return pqid;
    }

    public void setPqid(Long pqid) {
        this.pqid = pqid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}
}
