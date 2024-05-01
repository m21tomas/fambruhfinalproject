import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDto, User } from '../Class/user';
import { Product } from '../Class/product';
import { CartOrder } from '../Class/CartOrder';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  getProduct(productId: number) {
    throw new Error('Method not implemented.');
  }

  private baseUrl = 'http://localhost:9400';

  constructor(private http: HttpClient) { }

  public userSignUp(user: User): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/user/signup`, user);
  }
  public userSignUpWithReferral(userDto: RegisterDto, referralCode: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/user/signup/${referralCode}`, userDto);
  }
  public addProduct(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/product/add`, formData);
  }

  public getAllProduct(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/product/get/all`);
  }

  public getProductByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/product/getByName/${name}`);
  }

  public deleteProduct(pid: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/product/delete/${pid}`);
  }

  public findById(pid: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/product/getById/${pid}`);
  }

  public updateProduct(pid: number, product: Product): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/product/update/${pid}`, product);
  }
  setAvailability(pid: number, status: boolean): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/product/set-availability/${pid}`,status);
  }
  



//Order related apis

  public createOrder(CartOrder: CartOrder): Observable<CartOrder> {
    return this.http.post<CartOrder>(`${this.baseUrl}/order/create`, CartOrder);
  }

  public getOrderById(oid: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/order/get/orderInvoice/${oid}`);
  }

  public getAllOrders(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/order/get/all`);
  }

  public deleteOrder(oid: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/order/delete/${oid}`);
  }

  public getOrderByUsername(username: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/order/get/byUsername/${username}`);
  }

  changeUserOrderStatus(oid: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/order/changeStatus/${oid}?status=${status}`, null);
  }
}