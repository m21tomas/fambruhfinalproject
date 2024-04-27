import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Blog } from '../Class/blog';

@Injectable({
  providedIn: 'root'
})
export class BlogService {

  constructor(private http: HttpClient) { }

  baseUrl = 'http://localhost:9400/blog';

  // Blog related methods
  public addBlog(formData: FormData): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/add/blog`, formData);
  }
  

  public getAllBlogs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/get/all-blogs`);
  }

  public getBlogById(blogId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/get/blog/${blogId}`);
  }

  public getBlogByTitle(title: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/get/blogByTitle/${title}`);
  }

  public updateBlog(blogId: number, blog: Blog): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/update/blog/${blogId}`, blog);
  }

  public deleteBlog(blogId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/delete/blog/${blogId}`);
  }
}
