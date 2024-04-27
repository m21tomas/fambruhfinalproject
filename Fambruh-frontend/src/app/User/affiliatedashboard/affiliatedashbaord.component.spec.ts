import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AffiliatedashbaordComponent } from './affiliatedashbaord.component';

describe('AffiliatedashbaordComponent', () => {
  let component: AffiliatedashbaordComponent;
  let fixture: ComponentFixture<AffiliatedashbaordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffiliatedashbaordComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AffiliatedashbaordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
