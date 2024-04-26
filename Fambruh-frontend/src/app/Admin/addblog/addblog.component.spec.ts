import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddblogComponent } from './addblog.component';

describe('AddblogComponent', () => {
  let component: AddblogComponent;
  let fixture: ComponentFixture<AddblogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddblogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddblogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
