import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardsManagerComponent } from './cards-manager.component';

describe('CardsManagerComponent', () => {
  let component: CardsManagerComponent;
  let fixture: ComponentFixture<CardsManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardsManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardsManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
