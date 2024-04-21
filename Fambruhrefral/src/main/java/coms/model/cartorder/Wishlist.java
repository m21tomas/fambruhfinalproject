package coms.model.cartorder;

import javax.persistence.*;


import coms.model.product.Product;
import coms.model.user.User;
import coms.repository.Size;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int wishid;

    @ManyToOne
    private User user;

    @OneToOne
    private Product product;
    
    private Size productSize;

	public Wishlist() {
		super();
	}

	public Wishlist(User user, Product product, Size size) {
		super();
		this.user = user;
		this.product = product;
		this.productSize = size;
	}

	public int getWishid() {
		return wishid;
	}

	public void setWishid(int wishid) {
		this.wishid = wishid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Size getProductSize() {
		return productSize;
	}

	public void setProductSize(Size productSize) {
		this.productSize = productSize;
	}

    // Constructors, getters, and setters
}
