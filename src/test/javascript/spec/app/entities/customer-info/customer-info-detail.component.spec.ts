import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { CustomerInfoDetailComponent } from 'app/entities/customer-info/customer-info-detail.component';
import { CustomerInfo } from 'app/shared/model/customer-info.model';

describe('Component Tests', () => {
  describe('CustomerInfo Management Detail Component', () => {
    let comp: CustomerInfoDetailComponent;
    let fixture: ComponentFixture<CustomerInfoDetailComponent>;
    const route = ({ data: of({ customerInfo: new CustomerInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [CustomerInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CustomerInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CustomerInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load customerInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.customerInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
