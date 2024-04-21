package coms.model.cartorder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import coms.model.product.ProductQuantity;
import coms.repository.OrderStatus;

@Entity
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;

    private String username;
    private String firstname;
    private String lastname;
    private String address;
    private String district;
    private int pinCode;
    private String state;
    private String contact;
    private String date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String returnStatus; // Status for return functionality
    private String replacementStatus; // Status for replacement functionality
    private Double paidAmount;
    private String paymentMode;
    private boolean confirmationEmailSent = false; // New field for order confirmation email
    private boolean addressVerified; // New field for address verification

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ProductQuantity> products = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ComboProductQuantity> comboProducts = new HashSet<>();

    public UserOrder() {
    }

    public UserOrder(Long oid, String username, String firstname, String lastname, String address, String district,
			int pinCode, String state, String contact, String date, OrderStatus status, String returnStatus,
			String replacementStatus, Double paidAmount, String paymentMode, boolean confirmationEmailSent,
			boolean addressVerified, Set<ProductQuantity> products, Set<ComboProductQuantity> comboProducts) {
		super();
		this.oid = oid;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.district = district;
		this.pinCode = pinCode;
		this.state = state;
		this.contact = contact;
		this.date = date;
		this.status = status;
		this.returnStatus = returnStatus;
		this.replacementStatus = replacementStatus;
		this.paidAmount = paidAmount;
		this.paymentMode = paymentMode;
		this.confirmationEmailSent = confirmationEmailSent;
		this.addressVerified = addressVerified;
		this.products = products;
		this.comboProducts = comboProducts;
	}

	public UserOrder(String username, String address, String district, int pinCode, String state, String contact, String date,
			OrderStatus status, String returnStatus, String replacementStatus, Double paidAmount, String paymentMode,
			boolean confirmationEmailSent, boolean addressVerified, Set<ProductQuantity> products, 
			Set<ComboProductQuantity> comboProducts) {
        this.username = username;
        this.address = address;
        this.district = district;
        this.pinCode = pinCode;
        this.state = state;
        this.contact = contact;
        this.date = date;
        this.status = status;
        this.returnStatus = returnStatus;
        this.replacementStatus = replacementStatus;
        this.paidAmount = paidAmount;
        this.paymentMode = paymentMode;
        this.confirmationEmailSent = confirmationEmailSent;
        this.addressVerified = addressVerified;
        this.products = products;
        this.comboProducts = comboProducts;
    }

    // Getters and setters...

    public String getFirstname() {
		return firstname;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReplacementStatus() {
        return replacementStatus;
    }

    public void setReplacementStatus(String replacementStatus) {
        this.replacementStatus = replacementStatus;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public boolean isConfirmationEmailSent() {
        return confirmationEmailSent;
    }

    public void setConfirmationEmailSent(boolean confirmationEmailSent) {
        this.confirmationEmailSent = confirmationEmailSent;
    }

    public boolean isAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        this.addressVerified = addressVerified;
    }

    public Set<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductQuantity> products) {
        this.products = products;
    }

	public Set<ComboProductQuantity> getComboProducts() {
		return comboProducts;
	}

	public void setComboProducts(Set<ComboProductQuantity> comboProducts) {
		this.comboProducts = comboProducts;
	}
}
