import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupreferalComponent } from './signupreferal.component';

describe('SignupreferalComponent', () => {
  let component: SignupreferalComponent;
  let fixture: ComponentFixture<SignupreferalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignupreferalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SignupreferalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
