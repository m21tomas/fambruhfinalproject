import { Component, OnInit } from '@angular/core';
import { UserlistserviceService } from '../../service/userlistservice.service';
import { User } from '../../Class/user';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UserlistserviceService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAll().subscribe({
      next: (users: User[]) => {
        this.users = users;
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      }
    });
  }

  deleteUser(id: any): void {
    this.userService.delete(id).subscribe({
      next: () => {
        // Filter out the deleted user from the users array
        this.users = this.users.filter(user => user.userId !== id);
        console.log(`User with ID ${id} deleted successfully.`);
      },
      error: (error) => {
        console.error(`Error deleting user with ID ${id}:`, error);
      }
    });
  }
}