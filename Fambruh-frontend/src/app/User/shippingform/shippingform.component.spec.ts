import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShippingformComponent } from './shippingform.component';

describe('ShippingformComponent', () => {
  let component: ShippingformComponent;
  let fixture: ComponentFixture<ShippingformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ShippingformComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ShippingformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
