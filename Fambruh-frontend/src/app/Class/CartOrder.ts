import { CartcomboItem } from "./Cartcombo";
import { CartItem } from "./Cart";

export class CartOrder {
   
    username!: string;
    firstname!: string;
    lastname!: string;
    address!: string;
    district!: string;
    pinCode!: number;
    state!: string;
    contact!: string;
    paidAmount!: number;
    paymentMode!: string;
    cartItems: CartItem[] = [];
    cartcomboItems: CartcomboItem[] = [];
   // cartItem: OrderItem[] = [];
}