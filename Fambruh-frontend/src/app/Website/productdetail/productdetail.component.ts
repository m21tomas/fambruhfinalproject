import { AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, Renderer2, ViewEncapsulation } from '@angular/core';
import Swiper from 'swiper';
import { UserService } from '../../service/user.service';
import { ComboProduct, Product, ProductResponseDto, ProductSize } from '../../Class/product';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../navbar/navbar.component';
import { FooterComponent } from '../footer/footer.component';
import { CartService } from '../../service/cart.service';
import { LoginService } from '../../service/login.service';

import { User } from '../../Class/user';
import { CartItem } from '../../Class/Cart';
import { CartcomboItem } from '../../Class/Cartcombo';

@Component({
  selector: 'app-productdetail',
  templateUrl: './productdetail.component.html',
  standalone: true,
  imports: [FormsModule, CommonModule, NavbarComponent, FooterComponent],
  styleUrls: ['./productdetail.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProductdetailComponent implements OnInit, AfterViewInit, OnDestroy {
  public sizeChartVisible = false;
  public prevClass = '.prev';
  public nextClass = '.next';
  pid!: number;
  product!: ProductResponseDto;
  products: Product[] = []; // Array to hold products
  swiper!: Swiper | null;
  addToCartButtonDisabled = false;
  selectedSize1: ProductSize | undefined;
  selectedSize2: ProductSize | undefined;
  selectedSize: ProductSize | undefined;
  selectedProduct1: Product | null = null; // Property to hold selected product 1
  selectedProduct2: Product | null = null; // Property to hold selected product 2
  comboProduct: ComboProduct | null = null; // Property to hold the combo product
  constructor(
    private elementRef: ElementRef,
    private renderer: Renderer2,
    private productService: UserService,
    private route: ActivatedRoute,
    private cartservice: CartService,
    private loginservice: LoginService
  ) {   // Populate products array with your products data
    // For example:
    this.products.push(new Product(/* product1 data */));
    this.products.push(new Product(/* product2 data */));
    // Add more products as needed 
    this.getAllProduct();
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.pid = +params['id'];
      this.getProductDetail(this.pid);
    });
    window.scrollTo(0, 0); // Scroll to top when component initializes

   
  }


  ngAfterViewInit(): void {
    console.log('ngAfterViewInit() called');
    this.swiper = new Swiper('.product-slider', {
      direction: 'horizontal',
      loop: false,
      spaceBetween: 500,
      effect: 'fade',
      pagination: {
        el: '.swiper-pagination',
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      on: {
        init: () => {
          console.log('Swiper initialized');
          this.handleSlideChange();
        },
        slideChange: () => {
          console.log('Slide changed');
          this.handleSlideChange();
        }
      },
      scrollbar: {
        el: '.swiper-scrollbar',
      },
    });

    console.log('Swiper instance:', this.swiper); // Log Swiper instance for debugging
  }

  ngOnDestroy(): void {
    if (this.swiper) {
      this.swiper.destroy(true, true);
    }
  }

  getProductDetail(id: number): void {
    this.productService.findById(id).subscribe(product => {
      this.product = product;
      this.product.img1 = 'data:image/jpeg;base64,' + product.mainImage;
      this.product.img3 = 'data:image/jpeg;base64,' + product.detailImage;
      this.product.img4 = 'data:image/jpeg;base64,' + product.image1;
      this.product.img5 = 'data:image/jpeg;base64,' + product.image2;
      this.product.img6 = 'data:image/jpeg;base64,' + product.image3;
  console.log(this.product.img3);
    });
  }

  toggleSizeChart(): void {
    this.sizeChartVisible = !this.sizeChartVisible;
  }

  private handleSlideChange() {
    console.log('handleSlideChange() called');
    if (!this.swiper) {
      console.error('Swiper not initialized');
      return;
    }
    console.log('Swiper initialized successfully');
  }
   
  selectSize(size: string) {
    this.selectedSize = this.product.sizes.find(s => s.sizeName === size);
    this.selectedSize1 = this.product.sizes.find(s => s.sizeName === size);
    this.selectedSize2 = this.product.sizes.find(s => s.sizeName === size);
}
addToCart(product: Product, selectedSize: ProductSize) {
  console.log("Adding to cart:", product, selectedSize); // Log the product and selected size being added
  const cartItem = new CartItem(product, selectedSize); // Pass selected size to CartItem constructor
  console.log("Cart Item:", cartItem); // Log the created cart item
  this.cartservice.addToCart(cartItem);
}
addComboToCart(product1: Product, selectedSize1: ProductSize, product2: Product, selectedSize2: ProductSize) {
  console.log("Adding combo to cart:", product1, selectedSize1, product2, selectedSize2); // Log the combo details being added

  // Generate a unique ID for the combo item
  const comboId = this.generateComboId(product1, product2);

  // Create a new CartcomboItem with the selected products and sizes
  const comboItem = new CartcomboItem(comboId, product1, product2, selectedSize1, selectedSize2);
  
  console.log("Combo Item:", comboItem); // Log the created combo item

  // Add the combo item to the cart using the ComboService
  this.cartservice.addToComboCart(comboItem);
}

generateComboId(product1: Product, product2: Product): string {
  // Generate a unique ID based on the product IDs
  return `${product1.pid}_${product2.pid}`;
}






getAllProduct() {
  this.productService.getAllProduct().subscribe({
    next: (data) => {
      this.products = data;
      this.products.forEach((p) => {
        if (p.mainImage) { // Check if mainImage is defined
          p.img1 = 'data:image/jpeg;base64,' + p.mainImage;
          console.log('img1:',p.img1)
        }
      });
    },
    error: (error) => {
      console.error('Error fetching products:', error);
     
      alert('No Products Found');
    }
  });
}


  
  

  

  disableAddToCartButton(): void {
    this.addToCartButtonDisabled = true;
  }

  enableAddToCartButton(): void {
    this.addToCartButtonDisabled = false;
  }

  updateUIAfterAddToCart(): void {
    // Update the UI after adding product to cart
  }

  toggleFavorite(): void {
    const heart = this.elementRef.nativeElement.querySelector('.heart');
    if (heart) {
      heart.classList.toggle('is-active');
    }
  }
}
