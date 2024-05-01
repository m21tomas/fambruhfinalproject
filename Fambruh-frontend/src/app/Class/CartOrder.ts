import { CartItem } from "./Cart";
import { CartcomboItem } from "./Cartcombo";
import { OrderItem } from "./order-item";

export class CartOrder {
   
    username!: string;
    firstName!: string;
    lastName!: string;
    address!: string;
    district!: string;
    pinCode!: number;
    state!: string;
    contact!: string;
    paidAmount!: number;
    paymentMode!: string;
    cartItems: CartItem[] = [];
    cartcomboItems: CartcomboItem[] = [];

}