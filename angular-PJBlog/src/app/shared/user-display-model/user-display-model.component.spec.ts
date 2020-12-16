import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDisplayModelComponent } from './user-display-model.component';

describe('UserDisplayModelComponent', () => {
  let component: UserDisplayModelComponent;
  let fixture: ComponentFixture<UserDisplayModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserDisplayModelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDisplayModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
