import { Product } from "./product";
import { User } from "./user";

export class CartItemResponseDto {
    id!: number; // Assuming this represents the cartItemId
    quantity!: number;
    product!: Product; // Assuming you have a Product model
    // Exclude the User property
    user!:User;
  
}
