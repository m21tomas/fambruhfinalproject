package coms.model.product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import coms.repository.Size;


@Entity

public class ProductSize {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sizeId;

    @Enumerated(EnumType.STRING)
    private Size sizeName;

    @NotNull(message = "isAvailable cannot be null")
    @Column(name = "available", columnDefinition = "BOOLEAN")
    private boolean available;

    @ManyToMany(mappedBy = "sizes")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    public ProductSize() {
 		super();
 	}

	public ProductSize(Long sizeId, Size sizeName, @NotNull(message = "isAvailable cannot be null") boolean available,
			Set<Product> products) {
		super();
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.available = available;
		this.products = products;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Size getSizeName() {
		return sizeName;
	}

	public void setSizeName(Size sizeName) {
		this.sizeName = sizeName;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
    
}
