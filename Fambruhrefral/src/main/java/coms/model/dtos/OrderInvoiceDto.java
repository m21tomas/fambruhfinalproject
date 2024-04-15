package coms.model.dtos;

import coms.model.product.Product;

public class OrderInvoiceDto {
	private Product product;
	private int quantity;
    private String base64Image;
    
    public OrderInvoiceDto() {}
    
	public OrderInvoiceDto(Product product, int quantity, String base64Image) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.base64Image = base64Image;
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

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
}
