import { EmailValidator } from "@angular/forms";

export class User {
    userId!: number;
    username!: string;
    password!: string;
    referralCode!: string;
    referredByCode!: string;
    referredVerified!:boolean;
    enabled!:boolean;
    email!:string;
}

export class UserInfoResponse{
    userId!: number;
    username!: string;
    email!: string;
    referralCode!: string;
    referredByCode!: string;
    roles!: Authority[];
}

export class Authority {
    authority: string;
    constructor(authority: string) {
      this.authority = authority;
    }
  }


export class RegisterDto{
    username!: string;
    password!: string;
    email!:string;
}