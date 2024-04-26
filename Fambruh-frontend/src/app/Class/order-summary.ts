import { ProductQuantity, Product } from "./product";

export enum OrderStatus {
  Placed = 'Placed',
  Confirmed = 'Confirmed',
  Processing = 'Processing',
  OnTheWay = 'On the Way',
  Delivered = 'Delivered',
  Canceled = 'Canceled'
}

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
  status!: OrderStatus;
  date!: string;
  products: ProductQuantity[] = [];
}


export class OrderInvoiceDto {
  product!: Product;
  quantity!: number;
  detailImage!: string;


}
