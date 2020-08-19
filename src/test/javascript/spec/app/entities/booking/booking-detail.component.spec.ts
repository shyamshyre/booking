import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { BookingDetailComponent } from 'app/entities/booking/booking-detail.component';
import { Booking } from 'app/shared/model/booking.model';

describe('Component Tests', () => {
  describe('Booking Management Detail Component', () => {
    let comp: BookingDetailComponent;
    let fixture: ComponentFixture<BookingDetailComponent>;
    const route = ({ data: of({ booking: new Booking(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [BookingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BookingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load booking on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.booking).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
