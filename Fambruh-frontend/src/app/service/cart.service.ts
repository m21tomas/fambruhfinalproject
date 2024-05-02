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
  cartCountSubject: Subject<number> = new Subject<number>(); // Subject to emit cart count changes

  constructor() {
    // Load cart items from local storage on service initialization
    this.loadCartItems();
    this.loadComboCartItems();
  }

  addToCart(theCartItem: CartItem): number {
    console.log("Adding to cart:", theCartItem); // Log the item being added
    let existingCartItem: CartItem | undefined = undefined;
  
    // Check if the item is already in the cart
    existingCartItem = this.cartItems.find((item) => item.pid === theCartItem.pid);
  
    if (existingCartItem) {
      // If the item is already in the cart, check if the selected size is different
      if (existingCartItem.selectedSize.sizeId !== theCartItem.selectedSize.sizeId) {
        // If the selected size is different, add a new item to the cart with the new size
        this.cartItems.push(theCartItem);
        this.saveCartItems(); // Save cart items to local storage
        this.cartCountSubject.next(this.getCartCount()); // Update cart count
        this.calculateTotalPrice();
      }
    } else {
      // If the item is not in the cart, add it
      this.cartItems.push(theCartItem);
      this.saveCartItems(); // Save cart items to local storage
      this.cartCountSubject.next(this.getCartCount()); // Update cart count
      this.calculateTotalPrice();
    }
  
    this.calculateTotalPrice();
  
    // Return updated quantity (always 1 for a new item)
    return 1;
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
    this.cartCountSubject.next(this.getCartCount());
    this.calculateTotalPrice();
    this.saveCartItems(); // Save cart items to local storage
    return cartItem.quantity; // Return updated quantity
 
  }
  incrementQuantity(cartItem: CartItem): void {
    // Find the index of the cart item in the cartItems array
    const index = this.cartItems.findIndex(item => item.pid === cartItem.pid && item.selectedSize.sizeId === cartItem.selectedSize.sizeId);
  
    // If the item is found in the cart, increment its quantity
    if (index !== -1) {
      this.cartItems[index].quantity++;
    } else {
      // If the item with the same product ID but different size is not found, add a new item with the new size to the cart
      this.cartItems.push(cartItem);
    }
  
    this.saveCartItems(); // Save cart items to local storage
    this.calculateTotalPrice();
    this.cartCountSubject.next(this.getCartCount());
  }
  
  decrementComboQuantity(cartComboItem: CartcomboItem): number {
    cartComboItem.quantity--;
    if (cartComboItem.quantity < 1) {
      cartComboItem.quantity = 1; // Ensure quantity does not go below 1
    }
    this.cartCountSubject.next(this.getCartCount());
    this.calculateTotalPrice();
    this.saveComboCartItems(); // Save combo cart items to local storage
    return cartComboItem.quantity; // Return updated quantity
  }
  
  incrementComboQuantity(cartComboItem: CartcomboItem): void {
    // Find the index of the combo cart item in the cartcomboItems array
    const index = this.cartcomboItems.findIndex(item => item.comboId === cartComboItem.comboId && item.selectedSize1.sizeId === cartComboItem.selectedSize1.sizeId && item.selectedSize2.sizeId === cartComboItem.selectedSize2.sizeId);
  
    // If the item is found in the cart, increment its quantity
    if (index !== -1) {
      this.cartcomboItems[index].quantity++;
      this.saveComboCartItems(); // Save combo cart items to local storage
      this.calculateTotalPrice();
      this.cartCountSubject.next(this.getCartCount());
    }
  }
  
  
  getCartCount(): number {
    // Initialize a Set to keep track of unique items
    const uniqueItems = new Set<string>();
  
    // Iterate through individual items and add their IDs to the Set
    for (const item of this.cartItems) {
      uniqueItems.add(item.pid.toString()); // Assuming pid is of type string
    }
  
    // Iterate through combo items and add their comboIds to the Set
    for (const comboItem of this.cartcomboItems) {
      uniqueItems.add(comboItem.comboId.toString()); // Assuming comboId is of type string
    }
  
    // Return the size of the Set, which represents the number of unique items
    return uniqueItems.size;
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
      this.cartCountSubject.next(this.getCartCount());
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
    this.cartCountSubject.next(this.getCartCount());
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
    this.cartCountSubject.next(this.getCartCount());
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
