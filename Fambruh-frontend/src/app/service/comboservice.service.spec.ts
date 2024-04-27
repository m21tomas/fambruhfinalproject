import { TestBed } from '@angular/core/testing';

import { ComboserviceService } from './comboservice.service';

describe('ComboserviceService', () => {
  let service: ComboserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComboserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
