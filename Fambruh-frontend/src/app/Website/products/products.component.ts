import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, RouterModule } from '@angular/router';
import { ProductResponseDto } from '../../Class/product';
import { UserService } from '../../service/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs';

@Component({
  selector: 'app-products',
  imports:[RouterModule,CommonModule],
  standalone:true,
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {
  constructor(private blogService: UserService, private router: Router,private route: ActivatedRoute) {
    this.getAllProduct();
  }

  
  ngOnInit(): void {

    // Subscribe to route params to get the blog ID from the URL
    this.route.paramMap.subscribe((params: { get: (arg0: string) => any; }) => {
      const pid = params.get('id');
      if (pid) {
        this. getProductById(pid);
      }
    });


  }
  products: ProductResponseDto[] = [];
  selectedProduct: ProductResponseDto | undefined;
  
  getAllProduct() {
    this.blogService.getAllProduct().subscribe({
      next: (data: ProductResponseDto[]) => {
        this.products = data;
        this.products.forEach((p) => {
          if (p.mainImage) { // Check if mainImage is defined
            p.img1 = 'data:image/jpeg;base64,' + p.mainImage;
            //console.log('img1:',p.img1)
          }
        });
      },
      error: (error: any) => {
        console.error('Error fetching products:', error);
       
        alert('No Products Found');
      }
    });
  }
  

  getProductById(pid: any) {
    this.blogService.findById(pid).subscribe({
      next: (data: ProductResponseDto | undefined) => {
        this.selectedProduct = data;
        this.products.forEach((p) => {
          if (p.mainImage) { // Check if mainImage is defined
            p.img1 = 'data:image/jpeg;base64,' + p.mainImage;
          }
      });
    },
      error: (error: any) => {
        console.log(error);
        alert('product not found');
      }
    });
  }

  

}
