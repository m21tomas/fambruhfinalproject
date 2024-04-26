import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { CartItem } from '../Class/Cart';
import { CartcomboItem } from '../Class/Cartcombo';


@Injectable({
  providedIn: 'root'
})
export class CartService {
cartcomboItems:CartcomboItem[]=[];
  cartItems: CartItem[] = [];
  totalPrice: Subject<number> = new Subject<number>();
  totalQuantity: Subject<number> = new Subject<number>();

  constructor() {
    // Load cart items from local storage on service initialization
    this.loadCartItems();
    this.loadComboCartItems();
  }

  addToCart(theCartItem: CartItem): number {
    console.log("Adding to cart:", theCartItem); // Log the item being added
    let alreadyInCart: boolean = false;
    let existingCartItem: CartItem | undefined = undefined;
  
    if (this.cartItems.length > 0) {
      existingCartItem = this.cartItems.find((item) => item.pid === theCartItem.pid);
      alreadyInCart = (existingCartItem !== undefined);
    }
  
    if (alreadyInCart) {
      // If the item is already in the cart, update its quantity
      existingCartItem!.quantity++;

      // Check if the selected size is different and update it if needed
      if (existingCartItem!.selectedSize.sizeId !== theCartItem.selectedSize.sizeId) {
        existingCartItem!.selectedSize = theCartItem.selectedSize;
      }
    } else {
      // If the item is not in the cart, add it
      this.cartItems.push(theCartItem);
      this.saveCartItems(); // Save cart items to local storage
    }
  
    this.calculateTotalPrice();
  
    // Return updated quantity
    return alreadyInCart ? existingCartItem!.quantity : theCartItem.quantity;
}

  

calculateTotalPrice() {
  let totalPriceValue: number = 0;
  let totalQuantityValue: number = 0;

  // Calculate total price for individual products
  for (let currentCartItem of this.cartItems) {
    totalPriceValue += currentCartItem.quantity * currentCartItem.price;
    totalQuantityValue += currentCartItem.quantity;
  }

  // Calculate total price for combo products
  for (let currentComboItem of this.cartcomboItems) {
    totalPriceValue += currentComboItem.quantity * currentComboItem.price;
    totalQuantityValue += currentComboItem.quantity;
  }

  // Update the total price and quantity subjects
  this.totalPrice.next(totalPriceValue);
  this.totalQuantity.next(totalQuantityValue);
}

  decrementQuantity(cartItem: CartItem): number {
    cartItem.quantity--;
    if (cartItem.quantity < 1) {
      cartItem.quantity = 1; // Ensure quantity does not go below 1
    }
    this.calculateTotalPrice();
    this.saveCartItems(); // Save cart items to local storage
    return cartItem.quantity; // Return updated quantity
  }
  
  

  remove(cartItem: CartItem) {
    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.pid === cartItem.pid);
    if (itemIndex > -1) {
      this.cartItems.splice(itemIndex, 1);
      this.calculateTotalPrice();
      this.saveCartItems(); // Save cart items to local storage
    }
  }

  private saveCartItems() {
    localStorage.setItem('cartItems', JSON.stringify(this.cartItems));
  }

  private loadCartItems() {
    const savedCartItems = localStorage.getItem('cartItems');
    if (savedCartItems) {
      this.cartItems = JSON.parse(savedCartItems);
      this.calculateTotalPrice(); // Recalculate total price and quantity
    }
  }




  addToComboCart(theCartItem: CartcomboItem): number {
    console.log("Adding to combo cart:", theCartItem); // Log the item being added
    let alreadyInCart: boolean = false;
    let existingCartItem: CartcomboItem | undefined = undefined;
  
    if (this.cartcomboItems.length > 0) {
      existingCartItem = this.cartcomboItems.find((item) => item.comboId === theCartItem.comboId);
      alreadyInCart = (existingCartItem !== undefined);
    }
  
    if (alreadyInCart) {
      // If the item is already in the cart, update its quantity
      existingCartItem!.quantity++;

      // Check if the selected size is different and update it if needed
      if (existingCartItem!.selectedSize1.sizeId !== theCartItem.selectedSize1.sizeId) {
        existingCartItem!.selectedSize1 = theCartItem.selectedSize1;
      }
      if (existingCartItem!.selectedSize2.sizeId !== theCartItem.selectedSize2.sizeId) {
        existingCartItem!.selectedSize2 = theCartItem.selectedSize2;
      }
    } else {
      // If the item is not in the cart, add it
      this.cartcomboItems.push(theCartItem);
      this.saveComboCartItems(); // Save cart items to local storage
    }
  
    this.calculateTotalPrice();
  
    // Return updated quantity
    return alreadyInCart ? existingCartItem!.quantity : theCartItem.quantity;
}
removeCombo(cartItem: CartcomboItem) {
  const itemIndex = this.cartcomboItems.findIndex(tempCartItem => tempCartItem.comboId === cartItem.comboId);
  if (itemIndex > -1) {
    this.cartcomboItems.splice(itemIndex, 1);
    this.calculateTotalPrice();
    this.saveComboCartItems(); // Save cart items to local storage
  }
}

private saveComboCartItems() {
  localStorage.setItem('cartComboItems', JSON.stringify(this.cartcomboItems));
}

private loadComboCartItems() {
  const savedCartItems = localStorage.getItem('cartComboItems');
  if (savedCartItems) {
    this.cartcomboItems = JSON.parse(savedCartItems);
    this.calculateTotalPrice(); // Recalculate total price and quantity
  }
}



}
