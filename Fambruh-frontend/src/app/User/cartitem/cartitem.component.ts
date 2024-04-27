import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../Website/footer/footer.component';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { Router, RouterLink, RouterModule } from '@angular/router';

import { CartService } from '../../service/cart.service';
import { CartItemResponseDto } from '../../Class/CartItemResponseDto';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../service/login.service';
import { Product } from '../../Class/product';
import { CartItem } from '../../Class/Cart';
import { CartcomboItem } from '../../Class/Cartcombo';

@Component({
  selector: 'app-cartitem',
  standalone: true,
  imports: [FooterComponent,NavbarComponent,RouterModule,RouterLink,CommonModule],
  templateUrl: './cartitem.component.html',
  styleUrl: './cartitem.component.scss'
})
export class CartitemComponent implements OnInit {
  cartItems: CartItem[] = [];
  cartcomboItems: CartcomboItem[] = [];
  User: any;
  totalPrice: number = 0;
  totalQuantity: number = 0;
  public emptyCart: boolean=true;


  constructor(private cartService: CartService,private router: Router,
    private loginService: LoginService,private cdr:ChangeDetectorRef) { }

  ngOnInit(): void {
    console.log("cartitems",this.cartItems);
  this.cartDetails();
  this.combocartDetails();
  }
  
  cartDetails() {
    this.cartItems = this.cartService.cartItems;

    this.cartService.totalPrice.subscribe(data => this.totalPrice = data);
    this.cartService.totalQuantity.subscribe(data => this.totalQuantity = data);
   
    this.cartService.calculateTotalPrice();
  }
  removeItem(cartItem: CartItem) {
    this.cartService.remove(cartItem);
  }
  combocartDetails() {
    this.cartcomboItems = this.cartService.cartcomboItems;

    this.cartService.totalPrice.subscribe(data => this.totalPrice = data);
    this.cartService.totalQuantity.subscribe(data => this.totalQuantity = data);
   
    this.cartService.calculateTotalPrice();
  }
  comboremoveItem(cartcomboItem: CartcomboItem) {
    this.cartService.removeCombo(cartcomboItem);
  }
  private calculateTotal() {
    console.log("Calculating total...");
    this.totalPrice = this.cartItems.reduce((total, item) => total + (item.quantity * item.price), 0);
    this.totalQuantity = this.cartItems.reduce((total, item) => total + item.quantity, 0);
    console.log("Total price:", this.totalPrice);
    console.log("Total quantity:", this.totalQuantity);
  }
  quantity: number = 1; // Initial quantity
  increamentQuantity(cartItem: CartItem) {
    this.cartService.addToCart(cartItem);
  }

  incrementQuantity(cartItem: CartItem) {
    const updatedQuantity = this.cartService.addToCart(cartItem);
    console.log("Updated Quantity:", updatedQuantity); // Log the returned value
    this.quantity = updatedQuantity; // Update quantity
    this.cdr.detectChanges(); // Trigger change detection
  }
  
  

  decrementQuantity(cartItem: CartItem) {
    const updatedQuantity = this.cartService.decrementQuantity(cartItem);
    if (updatedQuantity !== 0) {
      this.quantity = updatedQuantity; // Update quantity
    }
  }
  
  navigateToC() {
    this.router.navigate(['/cart/shippingaddress']);
  }
  


}











