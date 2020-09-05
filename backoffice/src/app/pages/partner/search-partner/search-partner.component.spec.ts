import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchPartnerComponent } from './search-partner.component';

describe('SearchPartnerComponent', () => {
  let component: SearchPartnerComponent;
  let fixture: ComponentFixture<SearchPartnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchPartnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPartnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
