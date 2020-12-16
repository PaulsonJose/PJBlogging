import { TestBed } from '@angular/core/testing';

import { PjblogService } from './pjblog.service';

describe('PjblogService', () => {
  let service: PjblogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PjblogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
