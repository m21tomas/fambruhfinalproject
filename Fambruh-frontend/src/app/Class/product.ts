
export class Product {
    
    pid!: number;
    name!: string;
    brand!: string;
    description!: string;
    salt!: string;
    totalAvailable!: number;
    price!: number;
    discountedPrice!: number; // Corrected attribute name
    available!: boolean;
    mainImage: any;
    hoverImage: any;
    detailImage: any;
    image1: any;
    image2: any;
    image3: any;
    img1!:any;
    img2!:any;
    img3!:any;
    img4!:any;
    img5!:any;
    img6!:any;
    sizes: ProductSize[];
    quantity: ProductQuantity[];
    state: any;
    settings: any;
  
    constructor() {
      this.sizes = [
        new ProductSize('S', false),
        new ProductSize('M', false),
        new ProductSize('L', false),
        new ProductSize('XL', false)
      ];
      this.quantity = []; // Initialize quantity array
      this.state = {}; // Initialize state object
      this.settings = {}; // Initialize settings object
    }
  }
  
  export class ProductSize {
    sizeId!: number;
    sizeName: string;
    available: boolean;
  
    constructor(sizeName: string, available: boolean) {
      this.sizeName = sizeName;
      this.available = available;
    }
  }
  
  export enum Size {
    S = 'S',
    M = 'M',
    L = 'L',
    XL = 'XL'
    // Add other sizes as needed
  }
  

export class ProductResponseDto {
    [x: string]: any;
    pid!: number;
    name!: string;
    brand!: string;
    description!: string;
    salt!: string;
    totalAvailable!: number;
    discountedPrice!: number; // Corrected attribute name
    price!: number;
    available!: boolean;
    mainImage!:any;
    hoverImage!:any;
    detailImage!:any;
    image1!:any;
    image2!:any;
    image3!:any;
    img1!:any;
    img2!:any;
    img3!:any;
    img4!:any;
    img5!:any;
    img6!:any;
    sizes!: ProductSize[];
    quantity!:ProductQuantity[];
    state!:any;
    settings!:any;
  }

export class SQuantity {
    sqid!: number;
    product!: Product;
    quantity!: number;
}

export class MQuantity {
    mqid!: number;
    product!: Product;
    quantity!: number;
}

export class LQuantity {
    lqid!: number;
    product!: Product;
    quantity!: number;
}

export class XLQuantity {
    xlqid!: number;
    product!: Product;
    quantity!: number;
}

export class ProductQuantity {
    pqid!: number;
    product!: Product;
    quantity!: number;
    size!: string; // Define the 'size' property here
    hoverImage!:any;
    img2!:any;
}
export class ComboProduct {
    comboid!: number;
    product1!: Product;
    product2!: Product;
    selectedSize1!:ProductSize;
    selectedSize2!:ProductSize;
}
