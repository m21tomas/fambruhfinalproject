import { ProductQuantity, Product } from "./product";



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
  updateStatus(newStatus: string): void {
    this.status = newStatus;
  }
}


export class OrderInvoiceDto {
  product!: Product;
  quantity!: number;
  detailImage!: string;


}
