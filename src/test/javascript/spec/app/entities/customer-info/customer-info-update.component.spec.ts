import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { CustomerInfoUpdateComponent } from 'app/entities/customer-info/customer-info-update.component';
import { CustomerInfoService } from 'app/entities/customer-info/customer-info.service';
import { CustomerInfo } from 'app/shared/model/customer-info.model';

describe('Component Tests', () => {
  describe('CustomerInfo Management Update Component', () => {
    let comp: CustomerInfoUpdateComponent;
    let fixture: ComponentFixture<CustomerInfoUpdateComponent>;
    let service: CustomerInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [CustomerInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CustomerInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerInfo(123);
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
        const entity = new CustomerInfo();
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
