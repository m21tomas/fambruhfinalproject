package coms.model.cartorder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.product.ProductQuantity;
import coms.model.user.User;

@Entity
public class CartItemBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    
    @OneToMany(cascade= {CascadeType.ALL}, mappedBy = "cartItem")
    private List<ProductQuantity> productQuantities = new ArrayList<>();

    @OneToMany(cascade= {CascadeType.ALL}, mappedBy = "cartItem")
    private List<ComboProductQuantity> comboProductQuantities = new ArrayList<>();
    
    public CartItemBack() {
        super();
    }
    
    public CartItemBack(User user, List<ProductQuantity> productQuantities) {
		super();
		this.user = user;
		this.productQuantities = productQuantities;
	}

    public CartItemBack(User user, List<ProductQuantity> productQuantities,
			List<ComboProductQuantity> comboProductQuantities) {
		super();
		this.user = user;
		this.productQuantities = productQuantities;
		this.comboProductQuantities = comboProductQuantities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ProductQuantity> getProductQuantities() {
		return productQuantities;
	}

	public void setProductQuantities(List<ProductQuantity> productQuantities) {
		this.productQuantities = productQuantities;
	}

	public List<ComboProductQuantity> getComboProductQuantities() {
		return comboProductQuantities;
	}

	public void setComboProductQuantities(List<ComboProductQuantity> comboProductQuantities) {
		this.comboProductQuantities = comboProductQuantities;
	}
}
