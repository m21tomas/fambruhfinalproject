import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { FooterComponent } from '../../Website/footer/footer.component';
import { ActivatedRoute } from '@angular/router';
import { OrderSummary } from '../../Class/order-summary';
import { UserService } from '../../service/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-orderinvoice',
  standalone: true,
  imports: [NavbarComponent,FooterComponent,CommonModule],
  templateUrl: './orderinvoice.component.html',
  styleUrl: './orderinvoice.component.css'
})
export class OrderinvoiceComponent implements OnInit {
  oid!: number;
  orderInvoice: OrderSummary = new OrderSummary();

  constructor(private route: ActivatedRoute, private userService: UserService) { }
  ngOnInit(): void {
    this.oid = this.route.snapshot.params['oid'];
    this.getOrderConfirmation();
  }
  getOrderConfirmation() {
    this.userService.getOrderById(this.oid).subscribe({
      next: (data) => {
        this.orderInvoice = data;
        this.orderInvoice.products.forEach((p) => {
          p.product.img2 = 'data:image/jpeg;base64,' + p.product.hoverImage;
        })
      }, error: (error) => {
        console.log(error);
      }
    })
  }



}
