import { Component, ViewEncapsulation } from '@angular/core';
import { OrderSummary } from '../../Class/order-summary';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserService } from '../../service/user.service';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { Product } from '../../Class/product';


@Component({
  selector: 'app-orderplaced',
  standalone: true,
  imports: [CommonModule,NavbarComponent,RouterLink],
  templateUrl: './orderplaced.component.html',
  styleUrl: './orderplaced.component.scss'
})
export class OrderplacedComponent {
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
          p.product.img2 = 'data:image/jpeg;base64,' + p.product.hoverImage.imageData;
        })
      }, error: (error) => {
        console.log(error);
      }
    })
  }

}
