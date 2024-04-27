import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { Wishlist, WishlistResponseDto } from '../../Class/wishlist-item';
import { Router } from '@angular/router';
import { CartService } from '../../service/cart.service';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-wislist',
  standalone: true,
  imports: [NavbarComponent,CommonModule],
  templateUrl: './wislist.component.html',
  styleUrl: './wislist.component.scss'
})
export class WislistComponent {}