import { CartItem } from "./Cart";
import { CartcomboItem } from "./Cartcombo";
import { OrderItem } from "./order-item";

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

}