package coms.model.product;

import javax.persistence.*;

import coms.repository.Size;

@Entity
public class ComboProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Product product1;
    
    @Enumerated(EnumType.STRING)
    private Size size1;

    @OneToOne
    private Product product2;
    // Additional fields, constructors, getters, and setters...
    
    @Enumerated(EnumType.STRING)
    private Size size2;

    public ComboProduct() {
        // Default constructor for JPA
    }

    public ComboProduct(Product product1, Product product2, Size size1, Size size2) {
        this.product1 = product1;
        this.product2 = product2;
        this.size1 = size1;
        this.size2 = size2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct1() {
        return product1;
    }

    public void setProduct1(Product product1) {
        this.product1 = product1;
    }

    public Product getProduct2() {
        return product2;
    }

    public void setProduct2(Product product2) {
        this.product2 = product2;
    }

	public Size getSize1() {
		return size1;
	}

	public void setSize1(Size size1) {
		this.size1 = size1;
	}

	public Size getSize2() {
		return size2;
	}

	public void setSize2(Size size2) {
		this.size2 = size2;
	}
}
