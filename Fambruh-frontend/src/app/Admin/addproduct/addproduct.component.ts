import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Product, ProductSize } from '../../Class/product';
import { UserService } from '../../service/user.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-addproduct',
  standalone: true,
  imports: [CommonModule,RouterModule,FormsModule],
  templateUrl: './addproduct.component.html',
  styleUrl: './addproduct.component.css'
})
export class AddproductComponent{

  constructor(private blogService: UserService, private router: Router,private route:ActivatedRoute) {
    this.getAllProduct();
    this.pid = this.route.snapshot.params['pid'];
    this.getProduct();
  }
  status!: boolean; // Declare the availability property
  products: Product[] = [];
  prod: Product = new Product();
  Blogtitle!: string;
isValid!:boolean;
message!:string;
pid!: number;
  


getProduct() {
  this.blogService.findById(this.pid).subscribe({
    next: (data) => {
      this.prod = data;
    }, error: (error) => {
      console.log(error);
    }
  })
}

updateStatus() {
  this.blogService.setAvailability(this.pid, this.status).subscribe({
    next: (data) => {
      this.isValid = true;
      this.message = 'Medicine details updated successfully!';
    }, error: (error) => {
      this.isValid = false;
      this.message = 'Something went wrong!';
    }
  })
}


  
  

  getAllProduct() {
    this.blogService.getAllProduct().subscribe({
      next: (data) => {
        this.products = data;
      },
      error: (error) => {
        console.log(error);
        alert('No Products Found');
      }
    });
  }

  deleteProduct(id: number) {
    if (confirm('Are you sure you want to delete this product?')) {
      this.blogService.deleteProduct(id).subscribe({
        next: () => {
          this.getAllProduct();
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  updateProduct(pid: number) {
    let url = "/productform/" + pid; // Assuming "updateproduct" is the route for updating a product
    this.router.navigateByUrl(url);
  }

  onClick() {
    window.location.reload();
  }
  
  getProductSizes(sizes: ProductSize[]): string[] {
    if (sizes && Array.isArray(sizes)) {
      return sizes.map(size => size.sizeName);
    } else {
      return [];
    }
  }
  

}