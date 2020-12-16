import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePjblogComponent } from './create-pjblog.component';

describe('CreatePjblogComponent', () => {
  let component: CreatePjblogComponent;
  let fixture: ComponentFixture<CreatePjblogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatePjblogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePjblogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
