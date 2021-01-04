import { TestBed } from '@angular/core/testing';

import { ViewPjblogService } from './view-pjblog.service';

describe('ViewPjblogService', () => {
  let service: ViewPjblogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ViewPjblogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
