import { Component, OnInit } from '@angular/core';
import { OrderStatus, OrderSummary } from '../../Class/order-summary';
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
 
  constructor(private orderService: UserService,private route:ActivatedRoute) {}
  statusOptions: OrderStatus[] = [
    OrderStatus.Placed,
    OrderStatus.Confirmed,
    OrderStatus.Processing,
    OrderStatus.OnTheWay,
    OrderStatus.Delivered,
    OrderStatus.Canceled
  ];
  ngOnInit(): void {
    this.loadOrders();
   
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


}