package coms.model.cartorder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import coms.model.product.ComboProduct;
import coms.model.product.Product;
import coms.model.user.User;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

   
    @ManyToOne
    private User user;

    private int quantity;

    @OneToOne
    private ComboProduct comboproduct;

    public CartItem() {
        super();
    }

    // Constructor for cart item with combo product
    public CartItem(ComboProduct comboproduct, User user) {
        super();
        this.user = user;
        this.comboproduct = comboproduct;
    }

    // Constructor for cart item with single product
    public CartItem(User user, Product product, int quantity) {
        super();
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

    // Getters and setters

    public ComboProduct getComboproduct() {
        return comboproduct;
    }

    public void setComboproduct(ComboProduct comboproduct) {
        this.comboproduct = comboproduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	
}
