import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../Website/navbar/navbar.component';
import { User } from '../../Class/user';


@Component({
  selector: 'app-signupreferal',
  templateUrl: './signupreferal.component.html',
  standalone:true,
  imports:[FormsModule,CommonModule,ReactiveFormsModule],
  styleUrls: ['./signupreferal.component.css']
})
export class SignupreferalComponent implements OnInit {
  signUpForm: FormGroup;
  referralCode!: string;
  user: User = new User();
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.signUpForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.referralCode = params['referralCode'];
    });
  }

  onSubmit() {
    if (this.signUpForm.invalid) {
      return;
    }

    this.userService.userSignUpWithReferral(this.signUpForm.value, this.referralCode)
      .subscribe(
        () => {
          // Redirect to the login page or any other appropriate page upon successful signup
          this.router.navigate(['/login']);
        },
        error => {
          // Handle signup error
          console.error('Error signing up:', error);
        }
      );
  }
}