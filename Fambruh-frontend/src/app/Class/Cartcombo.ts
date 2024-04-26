import { ProductSize } from "./product";

export class CartcomboItem {
    comboId!: string;
    pid1!: number;
    pid2!: number;
    name!: string;
    price!: number;
    quantity!: number;
    selectedSize1!: ProductSize;
    selectedSize2!: ProductSize;
img3!:any;
    constructor(comboId: string, product1: any, product2: any, selectedSize1: ProductSize, selectedSize2: ProductSize) {
        this.comboId = comboId;
        this.pid1 = product1.pid;
        this.pid2 = product2.pid;
        this.name = `${product1.name} + ${product2.name}`;
        this.price = product1.price + product2.price;
        this.quantity = 1;
        this.selectedSize1 = selectedSize1;
        this.selectedSize2 = selectedSize2;
        this.img3=product1.img3;
        this.img3=product2.img3;
    }
}
