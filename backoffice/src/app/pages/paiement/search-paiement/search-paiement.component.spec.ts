import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPaiementComponent } from './search-paiement.component';

describe('SearchPaiementComponent', () => {
  let component: SearchPaiementComponent;
  let fixture: ComponentFixture<SearchPaiementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchPaiementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPaiementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
