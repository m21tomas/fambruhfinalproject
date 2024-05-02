import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Credentials } from '../Class/credentials';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor(private http: HttpClient) { }

  baseUrl = 'http://localhost:9400';

  public generateToken(credentials: Credentials): Observable<any> {
    console.log('Generating token...');
    return this.http.post<any>(`${this.baseUrl}/generate-token`, credentials);
  }

}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  baseUrl = 'http://localhost:9400';

  public getCurrentUser(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/current-user`);
  }

  public generateToken(credentials: Credentials): Observable<any> {
    console.log('Generating token...');
    return this.http.post<any>(`${this.baseUrl}/generate-token`, credentials);
  }

  public loginUser(token: any) {
    console.log('Logging in user...');
    localStorage.setItem('token', token);
    return true;
  }

  public isLoggedIn() {
    let tokenStr = localStorage.getItem('token');
    return tokenStr !== undefined && tokenStr !== null && tokenStr !== '';
  }

  public logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    localStorage.removeItem('cartCount');
    return true;
  }

  public logoutadmin() {
    localStorage.removeItem('token');
    localStorage.removeItem('admin');
    return true;
  }

  public getToken() {
    return localStorage.getItem('token');
  }

  public setUserDetails(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUserDetails() {
    let user = localStorage.getItem('user');
    if (user != null) {
      console.log('User details retrieved from local storage:', user);
      return JSON.parse(user);
    } else {
      this.logout();
      console.log('No user details found in local storage');
      return null;
    }
  }

  public getUserRole() {
    let user = this.getUserDetails();
    return user.authorities[0].authority;
  }

  forgotPassword(email: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/user/forgot-password?email=${email}`);
  }

  resetPassword(email: string, token: string, newPassword: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/user/reset-password?email=${email}&token=${token}&newPassword=${newPassword}`);
  }

}

@Injectable({
  providedIn: 'root'
})
export class ReferralService {

  constructor(private http: HttpClient) { }

  baseUrl = 'http://localhost:9400';

  public getUserByUsername(username: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/referral/username/${username}`);
  }

  public getAllByReferralCode(referralCode: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/referral/${referralCode}`);
  }

  public getMyReferredFriends(referralCode: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/referral/myReferredFriends/${referralCode}`);
  }

  public makeUserReferral(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/referral/becomeReferral`);
  }
  public getmessages(): Observable<any> {
      return this.http.get<any>(`${this.baseUrl}/referral/referralFriendsSignups`);
  }

}