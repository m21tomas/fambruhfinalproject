import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import { Router, ActivatedRoute, RouterModule, NavigationEnd } from '@angular/router';
import { User } from '../../Class/user';
import { LoginService } from '../../service/login.service';
import { CartService } from '../../service/cart.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  encapsulation: ViewEncapsulation.None 
})
export class NavbarComponent implements OnInit {
  user!: User;
  isNavOpen = false;
  username = '';
  totalPrice = 0;
  totalQuantity = 0;
  cartCount = 0;
  wishlistCount=0;
  constructor(
    private renderer: Renderer2,
    public loginService: LoginService,
    private router: Router,
    private cartservice: CartService
  ) {}

  ngOnInit(): void {

    this.updateMenuIconColor();
    window.addEventListener('resize', () => this.updateMenuIconColor());
    const userDetails = this.loginService.getUserDetails();
    if (userDetails) {
      this.username = userDetails.username;
      console.log("Username:", this.username);
    } else {
      console.log("User details not available.");
    }   

    // Subscribe to router events to close navbar on route change
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        // Close the navbar
        this.closeNav();
      }
    });
  }

  private isBackgroundLight(): boolean {
    const body = document.body;
    const backgroundColor = window.getComputedStyle(body).backgroundColor;
    const brightness = this.getBrightness(backgroundColor);
    return brightness > 128;
  }

  private getBrightness(color: string): number {
    const rgb = color.match(/\d+/g);
    if (rgb) {
      return (parseInt(rgb[0]) * 299 + parseInt(rgb[1]) * 587 + parseInt(rgb[2]) * 114) / 1000;
    } else {
      return 0;
    }
  }
  
  private updateMenuIconColor(): void {
    const menuIcon = document.querySelector('.menu-icon');
    const isLight = this.isBackgroundLight();
    this.renderer.setStyle(menuIcon, 'color', isLight ? 'black' : 'white');
  }

  toggleNav(): void {
    const body = document.body;
    const navContent = document.querySelector('.nav__content');
    body.classList.toggle('nav-active');
    this.renderer.setStyle(navContent, 'visibility', body.classList.contains('nav-active') ? 'visible' : 'hidden');
  }

  closeNav(): void {
    const body = document.body;
    const navContent = document.querySelector('.nav__content');
    body.classList.remove('nav-active');
    this.renderer.setStyle(navContent, 'visibility', 'hidden');
  }

  logout(): void {
    this.loginService.logout();
    window.location.reload();
  }

  getOrders(): void {
    let url = '/myorder/' + this.username;
    this.router.navigateByUrl(url);
  }
}