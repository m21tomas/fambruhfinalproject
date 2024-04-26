import { Product } from "./product";
import { User } from "./user";

export class Wishlist {
    wishid!: number;
    user!: User;
    product!: Product;
}
export class WishlistResponseDto{
    wishid!:number;
    user!: User;
    product!: Product;
}