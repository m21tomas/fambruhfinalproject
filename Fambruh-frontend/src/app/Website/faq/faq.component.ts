import { CommonModule } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [CommonModule,NavbarComponent],
  templateUrl: './faq.component.html',
  styleUrl: './faq.component.scss'
})
export class FaqComponent {
  selectedTab: string = 'tab1'; // Initialize active tab

  constructor() {}

  // Method to switch tabs
  selectTab(tab: string) {
    this.selectedTab = tab;
  }
}
