import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { FinanceDetailComponent } from 'app/entities/finance/finance-detail.component';
import { Finance } from 'app/shared/model/finance.model';

describe('Component Tests', () => {
  describe('Finance Management Detail Component', () => {
    let comp: FinanceDetailComponent;
    let fixture: ComponentFixture<FinanceDetailComponent>;
    const route = ({ data: of({ finance: new Finance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [FinanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FinanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load finance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.finance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
