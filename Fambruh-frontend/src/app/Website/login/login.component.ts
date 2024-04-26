import { Component,ViewEncapsulation } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar.component';
import { Router, RouterLink } from '@angular/router';
import { Credentials } from '../../Class/credentials';
import { User } from '../../Class/user';
import { LoginService } from '../../service/login.service';
import { UserService } from '../../service/user.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,NavbarComponent,CommonModule,RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials: Credentials = new Credentials();
  user: User = new User();
  isValid!: boolean;
  message!: string;

  constructor(
    private loginService: LoginService,
    private userService: UserService,
    private router: Router
  ) { }
  onLoginSubmit() {
    this.loginService.generateToken(this.credentials).subscribe({
      next: (response) => {
        // user is logged in
        this.loginService.loginUser(response.token);
        this.loginService.getCurrentUser().subscribe({
          next: (user) => {
            this.loginService.setUserDetails(user);
            // redirect to user home page
            if (this.loginService.getUserRole() == 'USER') {
              this.router.navigate(['/']);
            } else if (this.loginService.getUserRole() == 'ADMIN') {
              this.router.navigate(['/']);
            }
          }, error: (error) => {
            console.log(error);
          }
        })
      }, error: (error) => {
        console.log(error);
        alert('Invalid Credentials!');
      }
    })
  }

  onSignupSubmit() {
    this.userService.userSignUp(this.user).subscribe({
      next: (response) => {
        this.isValid = true;
        this.message = 'Successfully Registered! Sign In to Continue!';
      }, error: (error) => {
        console.log(error);
        this.isValid = false;
        this.message = 'E-mail address already exists!';
      }
    })
  }

  onClick() {
    this.router.navigate(['/login']);
  }
  goToforgotpassword() {
    this.router.navigate(['login', 'forgot-password']);
  }
}
