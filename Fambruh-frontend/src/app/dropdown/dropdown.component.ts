import { Component, ElementRef, HostListener } from '@angular/core';

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.css'
})
export class DropdownComponent {

  constructor(private elementRef: ElementRef) { }

  ngAfterViewInit() {
    this.setupEventListeners();
  }

  setupEventListeners() {
    // Click event for dropdown caption
    const caption = this.elementRef.nativeElement.querySelector('.dropdown > .caption');
    caption.addEventListener('click', () => {
      this.toggleDropdown();
    });
  
    // Click event for dropdown item
    const items = this.elementRef.nativeElement.querySelectorAll('.dropdown > .list > .item');
    items.forEach((item: HTMLElement) => {
      item.addEventListener('click', (event: MouseEvent) => {
        this.selectItem((event.target as HTMLElement).textContent!.trim());
      });
    });
  }
  
  @HostListener('document:keyup', ['$event'])
  handleKeyUpEvent(event: KeyboardEvent) {
    if (event.key === 'Escape') {
      this.closeDropdowns();
    }
  }
  
  @HostListener('document:click', ['$event'])
  handleDocumentClick(event: MouseEvent) {
    if (!this.elementRef.nativeElement.querySelector('.dropdown').contains(event.target)) {
      this.closeDropdowns();
    }
  }
  
  toggleDropdown() {
    const dropdown = this.elementRef.nativeElement.querySelector('.dropdown');
    if (dropdown) {
      dropdown.classList.toggle('open');
    }
  }
  
  selectItem(text: string) {
    const items = this.elementRef.nativeElement.querySelectorAll('.dropdown > .list > .item');
    items.forEach((item: HTMLElement) => {
      item.classList.remove('selected');
    });
    const caption = this.elementRef.nativeElement.querySelector('.dropdown > .caption');
    if (caption) {
      caption.textContent = text;
    }
    this.closeDropdowns();
  }
  
  closeDropdowns() {
    const dropdowns = this.elementRef.nativeElement.querySelectorAll('.dropdown');
    dropdowns.forEach((dropdown: HTMLElement) => {
      dropdown.classList.remove('open');
    });
  }
}