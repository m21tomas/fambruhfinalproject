import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WislistComponent } from './wislist.component';

describe('WislistComponent', () => {
  let component: WislistComponent;
  let fixture: ComponentFixture<WislistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WislistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WislistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
