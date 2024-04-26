import { Component } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../Website/navbar/navbar.component';

@Component({
  selector: 'app-passwordforgot',
  standalone: true,
  imports: [FormsModule,CommonModule,NavbarComponent],
  templateUrl: './passwordforgot.component.html',
  styleUrl: './passwordforgot.component.css'
})
export class PasswordforgotComponent {
  email: string = '';
  message!: string;

  constructor(private loginService: LoginService) { }
  
  
  forgotPassword() {
    this.loginService.forgotPassword(this.email).subscribe(
      () => {
        this.message = 'Password reset link sent to email.';
      },
      (error) => {
        console.error('Error occurred:', error); // Log the error for debugging
        this.message = error.error.message || 'An error occurred while sending email. Please try again later.';
      }
    );
  }
}