import { Component, OnInit } from '@angular/core';
import { OrderSummary } from '../../Class/order-summary';
import { UserService } from '../../service/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
  orders: OrderSummary[] = [];
  oid!: number;
  newStatus!: string;
  constructor(private orderService: UserService,private route:ActivatedRoute) {}
  
  ngOnInit(): void {
    this.loadOrders();
    this.route.params.subscribe(params => {
      this.oid = params['oid']; // Get oid from route parameters
      this.newStatus = params['status']; // Get newStatus from route parameters
      if (this.oid && this.newStatus) {
        this.changeStatus(this.oid, this.newStatus);
      }
    });
  }

  loadOrders(): void {
    this.orderService.getAllOrders().subscribe({
      next: (orders: OrderSummary[]) => {
        this.orders = orders;
      },
      error: (error) => {
        console.error('Error fetching orders:', error);
      }
    });
  }
  changeStatus(oid: number, newStatus: string): void {
    if (!oid || !newStatus) {
      console.error('Invalid oid or newStatus:', oid, newStatus);
      return;
    }
  
    this.orderService.changeUserOrderStatus(oid, newStatus)
      .subscribe(
        response => {
          console.log(response); // Handle response accordingly
          // Update UI or show success message
        },
        error => {
          console.error(error); // Handle error
          // Show error message or handle error condition
        }
      );
  }
  

}