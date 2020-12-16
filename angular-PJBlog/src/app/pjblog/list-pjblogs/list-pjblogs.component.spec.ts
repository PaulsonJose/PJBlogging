import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListPjblogsComponent } from './list-pjblogs.component';

describe('ListPjblogsComponent', () => {
  let component: ListPjblogsComponent;
  let fixture: ComponentFixture<ListPjblogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListPjblogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListPjblogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
