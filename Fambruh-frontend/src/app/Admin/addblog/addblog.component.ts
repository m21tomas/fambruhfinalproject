import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { Blog } from '../../Class/blog';
import { BlogService } from '../../service/blogservice.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-addblog',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './addblog.component.html',
  styleUrl: './addblog.component.css'
})
export class AddblogComponent implements OnInit {
  blogs: Blog[] = [];

  constructor(private blogService: BlogService, private router: Router) { }

  ngOnInit(): void {
    this.getAllBlogs();
  }

  getAllBlogs() {
    this.blogService.getAllBlogs().subscribe({
      next: (data) => {
        this.blogs = data.map(blog => {
          return {
            ...blog,
            img: 'data:image/jpeg;base64,' + blog.blogImage.imageData
          };
        });
      }, 
      error: (error) => {
        console.log(error);
        alert('No Blogs Found');
      }
    })
  }

  getTotalViews(): number {
    // You need to implement this method to calculate total views
    // This could be achieved by iterating through blogs and summing up the viewCount property
    return 0;
  }

  deleteProduct(id: number) {
    this.blogService.deleteBlog(id).subscribe({
      next: (data) => {
        this.getAllBlogs();
      }, 
      error: (error) => {
        console.log(error);
      }
    })
  }

  updateProduct(pid: number) {
    let url = "/updateblog/" + pid;
    this.router.navigateByUrl(url);
  }

  onClick() {
    window.location.reload();
  }
  
  deleteBlog(blogId: number) {
    this.blogService.deleteBlog(blogId).subscribe({
      next: () => {
        // Remove the deleted blog from the local array
        this.blogs = this.blogs.filter(blog => blog.id !== blogId);
        console.log('Blog deleted successfully');
      }, 
      error: (error) => {
        console.log(error);
        alert('Failed to delete blog');
      }
    })
  }

  updateBlog(blogId: number) {
    let url = "/updateblog/" + blogId;
    this.router.navigateByUrl(url);
  }
}