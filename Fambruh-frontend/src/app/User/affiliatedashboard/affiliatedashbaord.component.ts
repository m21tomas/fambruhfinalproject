import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit, ViewEncapsulation } from '@angular/core';
import { UserService } from '../../service/user.service';
import Chart from 'chart.js/auto';
import { Observable, catchError, throwError } from 'rxjs';
import { User, UserInfoResponse } from '../../Class/user';
import { LoginService, ReferralService } from '../../service/login.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-affiliatedashbaord',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './affiliatedashbaord.component.html',
  styleUrl: './affiliatedashbaord.component.scss'
})
export class AffiliatedashbaordComponent implements OnInit{
  selectedTab: string = 'tab1'; // Initialize active tab

  constructor(private referralService:ReferralService,private userService:LoginService,
    private router:Router,private route:ActivatedRoute) {}

  // Method to switch tabs
  selectTab(tab: string) {
    this.selectedTab = tab;
  }

  referredFriends!: any[]; // Assuming the type of referred friends is an array of any
  userInfo!:User;
  referralCode!:any;
  notifications: string[] = [];
  notificationsFound: boolean = false;
  showNotifications: boolean = true;
  notificationCount: number = 0; // Initialize notification count
  ngOnInit(): void {
    const userDetails = this.userService.getUserDetails(); // Get user details
    if (userDetails && userDetails.referralCode) {
      this.referralCode = userDetails.referralCode; // Assign referral code
      this.getProductDetail(this.referralCode); // Fetch referred friends
    } else {
      console.error('Error: User details or referralCode is undefined');
    }
    this.getReferralNotifications();
  }
  


  getReferralNotifications() {
    this.referralService.getmessages()
      .pipe(
        catchError(error => {
          console.error('Error getting referral notifications:', error);
          // Handle error if needed
          return throwError(error);
        })
      )
      .subscribe((response: any) => {
        if (response && response.length > 0) {
          this.notifications = response;
          this.notificationCount = response.length; // Update notification count
          this.notificationsFound = true;
        } else {
          this.notificationsFound = false;
        }
      });
  }

  

  getProductDetail(referralCode: string): void {
    this.referralService.getMyReferredFriends(referralCode).subscribe(referredFriends => {
      this.referredFriends = referredFriends;
  
  
    });
  }
  randomMessages: string[] = [
    "New notification 1",
    "New notification 2",
    "New notification 3",
    // Add more random messages as needed
  ];



  reloadNotifications() {
    // Hide notifications
    this.showNotifications = false;

    // Simulate reload delay (you can replace this with actual data loading)
    setTimeout(() => {
      // Select a random message
      const randomIndex = Math.floor(Math.random() * this.randomMessages.length);
      const randomMessage = this.randomMessages[randomIndex];

      // Update notifications with the random message
      this.notifications = [randomMessage];

      // Show notifications again after a short delay
      this.showNotifications = true;
    }, 1000); // Adjust the delay as needed
  }

}  



