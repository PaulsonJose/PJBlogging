import { TestBed } from '@angular/core/testing';

import { TockenInterceptor } from './tocken.interceptor';

describe('TockenInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      TockenInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: TockenInterceptor = TestBed.inject(TockenInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
