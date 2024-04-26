import { ProductSize } from "./product";

export class OrderItem {
    pid!: number;
    quantity!: number;
    selectedSize!: ProductSize;
    selectedSize1!: ProductSize;
    selectedSize2!: ProductSize;
}