import { Product, ProductSize } from "./product";

export class CartItem {
    pid!: number;
    name!: string;
    brand!: string;
    price!: number;
    img3!: any;
    quantity!: number;
    selectedSize!: ProductSize; // Add selectedSize property

 
    constructor(product: Product, selectedSize: ProductSize) {
        this.pid = product.pid;
        this.name = product.name;
        this.brand = product.brand;
        this.price = product.price;
        this.img3 = product.img3;
        this.quantity = 1;
        this.selectedSize = selectedSize;
    }
}