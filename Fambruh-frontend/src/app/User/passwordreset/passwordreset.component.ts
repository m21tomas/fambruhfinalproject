import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { LoginService } from '../../service/login.service';

@Component({
  selector: 'app-passwordreset',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './passwordreset.component.html',
  styleUrl: './passwordreset.component.css'
})
export class PasswordresetComponent {
  email: string = '';
  token: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';
  message: string = '';
  showPassword: boolean = false;

  constructor(private route: ActivatedRoute, private loginService: LoginService) {
    this.route.queryParams.subscribe(params => {
      this.email = params['email']; // Access email using bracket notation
      this.token = params['token']; // Access token using bracket notation
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
}

  resetPassword() {
    if (this.newPassword !== this.confirmNewPassword) {
      this.message = 'Passwords do not match.';
      return;
    }

    this.loginService.resetPassword(this.email, this.token, this.newPassword).subscribe(
      (response: any) => {
        this.message = response;
      },
      (error) => {
        console.error('Error occurred:', error);
        this.message = error.error.message || 'An error occurred while resetting password. Please try again later.';
      }
    );
  }
}