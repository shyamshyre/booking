import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { FinanceUpdateComponent } from 'app/entities/finance/finance-update.component';
import { FinanceService } from 'app/entities/finance/finance.service';
import { Finance } from 'app/shared/model/finance.model';

describe('Component Tests', () => {
  describe('Finance Management Update Component', () => {
    let comp: FinanceUpdateComponent;
    let fixture: ComponentFixture<FinanceUpdateComponent>;
    let service: FinanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [FinanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FinanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Finance(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Finance();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
