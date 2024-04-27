import { CommonModule, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';
import { Blog } from '../../Class/blog';
import { BlogService } from '../../service/blogservice.service';

@Component({
  selector: 'app-blogdetail',
  standalone: true,
  imports: [CommonModule,RouterLink,RouterModule],
  templateUrl: './blogdetail.component.html',
  styleUrl: './blogdetail.component.scss'
})
export class BlogdetailComponent implements OnInit {
  blogId!: number;
  blog!: Blog;

  constructor(private route: ActivatedRoute, private blogService: BlogService) { }

  ngOnInit(): void {
    // Retrieve the blog ID from the route parameters
    this.route.params.subscribe(params => {
      this.blogId = +params['id']; // Convert string to number
      // Use the blog ID to fetch the detailed information of the blog
      this.getBlogDetails(this.blogId);
    });
  }

  getBlogDetails(id: number): void {
    // Call the getBlogById method to retrieve the detailed information of the blog
    this.blogService.getBlogById(id).subscribe(blog => {
      this.blog = blog;
      // Set the image details similar to how it's done in getAllBlogs method
      this.blog.img = 'data:image/jpeg;base64,' + blog.blogImage.imageData;
    });
  }

  formatDate(date: Date): string {
    return formatDate(date, 'dd-MM-yyyy', 'en-IN'); // Format date to 'dd-MM-yyyy' for India
  }
}
