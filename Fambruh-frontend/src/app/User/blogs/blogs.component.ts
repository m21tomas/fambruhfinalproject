import { CommonModule, formatDate } from '@angular/common';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Blog } from '../../Class/blog';
import { BlogService } from '../../service/blogservice.service';
import { NavbarComponent } from '../../Website/navbar/navbar.component';

@Component({
  selector: 'app-blogs',
  standalone: true,
  imports: [RouterModule,CommonModule,NavbarComponent],
  templateUrl: './blogs.component.html',
  styleUrl: './blogs.component.scss',
  encapsulation:ViewEncapsulation.None
})
export class BlogsComponent  implements OnInit {

  blogs: Blog[] = [];
  selectedBlog: Blog | undefined;

  constructor(private blogService: BlogService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getAllBlogs();

    // Subscribe to route params to get the blog ID from the URL
    this.route.paramMap.subscribe(params => {
      const blogId = params.get('id');
      if (blogId) {
        this.getBlogById(blogId);
      }
    });
  }

  getAllBlogs() {
    this.blogService.getAllBlogs().subscribe({
      next: (data) => {
        this.blogs = data;
        this.blogs.forEach((p) => {
          p.img = 'data:image/jpeg;base64,' + p.blogImage.imageData;
        });
      }, 
      error: (error) => {
        console.log(error);
        alert('No blogs Found');
      }
    });
  }

  getBlogById(id: any) {
    this.blogService.getBlogById(id).subscribe({
      next: (data) => {
        this.selectedBlog = data;
      }, 
      error: (error) => {
        console.log(error);
        alert('Blog not found');
      }
    });
  }

  formatDate(date: Date): string {
    return formatDate(date, 'dd-MM-yyyy', 'en-IN'); // Format date to 'dd-MM-yyyy' for India
  }
}
