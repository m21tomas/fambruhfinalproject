import { ProductQuantity, Product, ComboProduct } from "./product";



export class OrderSummary {

  oid!: number;
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
  status!: string;
  date!: string;
  products: ProductQuantity[] = [];
comboproduct!:ComboProduct;
img2!:any;


}


export class OrderInvoiceDto {
  product!: Product;
  quantity!: number;
  detailImage!: string;


}
