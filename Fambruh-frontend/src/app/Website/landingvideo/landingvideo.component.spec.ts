import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingvideoComponent } from './landingvideo.component';

describe('LandingvideoComponent', () => {
  let component: LandingvideoComponent;
  let fixture: ComponentFixture<LandingvideoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LandingvideoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LandingvideoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
