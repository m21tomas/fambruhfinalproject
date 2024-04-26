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
    cartItem: OrderItem[] = [];
    combocartitem: CartcomboItem[] = [];

}