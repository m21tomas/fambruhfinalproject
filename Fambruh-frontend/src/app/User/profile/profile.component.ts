import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { LoginService,JwtService, ReferralService} from '../../service/login.service';
import { UserService } from '../../service/user.service';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent,CommonModule,RouterLink,NgxPaginationModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  selectedTab: string = 'tab1'; // Initialize active tab
  referralCodeGenerated: boolean = false; // Track if referral code is generated
  userDetails: any; // Variable to store user details
  referredUsers: any[] = [];
  referredVerified!: boolean; // Add referredVerified property
  username!: string;
  orders: any[] = [];
  recorders: any[] = [];
  page: number = 1;
  count: number = 0;
  tableSize: number = 3;
  event: any;

  onTableDataChange(event: any) {
    this.page = event;
  }
  constructor(private loginService: LoginService,private refralservice:ReferralService,
    private userservice:UserService, private router: Router) {} 
  ngOnInit(): void {
    this.getUserDetails(); // Call getUserDetails when the component initializes
    this.referralCodeGenerated = false; // Reset the referralCodeGenerated flag
    this.username = this.loginService.getUserDetails().username;
    this.getAllOrders();
    this.getrecentOrders();
  }

  // Method to switch tabs
  selectTab(tab: string) {
    this.selectedTab = tab;
  }

  getUserDetails() {
    this.userDetails = this.loginService.getUserDetails();
    console.log('User details:', this.userDetails);
  }
  generateReferralCode() {
    // Check if referral code already exists in userDetails
    if (this.userDetails.referralCode) {
      // Referral code already exists, set website link
      this.userDetails.websiteLink = `http://localhost:4200/signup/${this.userDetails.referralCode}`; // Use port 4200 for local development
      this.referralCodeGenerated = true; // Set flag to true
    } else {
      // Referral code does not exist, generate new referral code
      this.refralservice.makeUserReferral().subscribe(
        (response) => {
          this.userDetails.referralCode = response.referralCode; // Update user details with generated referral code
          this.userDetails.websiteLink = `http://localhost:4200/signup/${response.referralCode}`; // Set website link with new referral code
          this.referralCodeGenerated = true; // Set flag to true
        },
        (error) => {
          console.error('Error generating referral code:', error);
        }
      );
    }
  }
  
  getAllOrders() {
    this.userservice.getOrderByUsername(this.username).subscribe({
      next: (data) => {
        console.log(data); // Log the data received from the API
        // Reverse the array to make the newest orders appear first
        this.orders = data.reverse();
      },
      error: (error) => {
        console.log(error);
        alert('No Orders found');
      }
    });
  }
  
  getrecentOrders() {
    this.userservice.getOrderByUsername(this.username).subscribe({
      next: (data) => {
        console.log(data); // Log the data received from the API
        // Sort orders by date in descending order
        this.recorders = data.reverse();
        data.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime());
        // Concatenate the new orders array with the existing orders array
        this.recorders = data.concat(this.recorders);
        // Get only the first two orders
        this.recorders = this.recorders.slice(0, 2);
      },
      error: (error) => {
        console.log(error);
        alert('No Orders found');
      }
    });
  }
  
  getOrderDetails(oid: number) {
    let url = '/orderdetail/' + oid;
    this.router.navigateByUrl(url);
  }
  

  viewReferredUsers() {
    const referralCode = this.userDetails.referralCode;
    if (referralCode) {
      this.refralservice.getMyReferredFriends(referralCode).subscribe(
        (users) => {
          console.log('Referred users:', users);
          this.referredUsers = users; // Populate referredUsers array with data
        },
        (error) => {
          console.error('Error getting referred users:', error);
        }
      );
    } else {
      console.error('Referral code not found in user details');
    }
  }
  
  logout() {
    // Display the confirmation dialog
    const confirmation = confirm('Are you sure you want to logout?');

    // If the user confirms, proceed with logout
    if (confirmation) {
      const confirmation = this.loginService.logout();
      if (confirmation) {
        console.log('User confirmed logout');
        this.router.navigate(['/']);
      } else {
        console.log('User canceled logout');
      }
    } else {
      console.log('User canceled logout');
    }
  }
}
