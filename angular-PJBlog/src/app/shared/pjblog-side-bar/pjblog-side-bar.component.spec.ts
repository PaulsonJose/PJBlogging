import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PjblogSideBarComponent } from './pjblog-side-bar.component';

describe('PjblogSideBarComponent', () => {
  let component: PjblogSideBarComponent;
  let fixture: ComponentFixture<PjblogSideBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PjblogSideBarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PjblogSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
