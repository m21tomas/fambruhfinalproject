import { Component } from '@angular/core';
import { NavbarComponent } from '../Website/navbar/navbar.component';
import { LandingvideoComponent } from '../Website/landingvideo/landingvideo.component';
import { FooterComponent } from '../Website/footer/footer.component';
import { ProductsComponent } from '../Website/products/products.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavbarComponent,LandingvideoComponent,FooterComponent,ProductsComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
