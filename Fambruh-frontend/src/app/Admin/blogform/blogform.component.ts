import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Blog } from '../../Class/blog';
import { BlogService } from '../../service/blogservice.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-blogform',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './blogform.component.html',
  styleUrl: './blogform.component.css'
})
export class BlogformComponent {
  constructor(private blogservice: BlogService, private router: Router) {}

  blog: Blog = new Blog();
  file!: File; // Change type to File
  isValid!: boolean;
  message!: string;
  onSubmit() {
    const formData = new FormData();
    formData.append('blog', JSON.stringify(this.blog));
    formData.append('image', this.file);
  
    this.blogservice.addBlog(formData).subscribe({
      next: (response) => {
        this.isValid = true;
        this.message = "Blog added successfully!";
      }, 
      error: (error) => {
        this.isValid = false;
        this.message = 'Something went wrong!'
      }
    });
  }
  

  onChangeFileField(event: any) {
    const files = event.target.files;
    if (!files || files.length === 0) {
        // No file selected
        return;
    }
    const file = files[0];
    const maxSizeInBytes = 30 * 1024 * 1024; // 30 MB in bytes
    if (file.size > maxSizeInBytes) {
        // Display an error message or prevent further processing
        console.log('File size exceeds the limit.');
        return;
    }
    this.file = file; // Assign File directly
}

  onClick() {
    this.router.navigate(['/admindashboard']);
  }
}
