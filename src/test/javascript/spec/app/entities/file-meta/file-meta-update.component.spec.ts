import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { FileMetaUpdateComponent } from 'app/entities/file-meta/file-meta-update.component';
import { FileMetaService } from 'app/entities/file-meta/file-meta.service';
import { FileMeta } from 'app/shared/model/file-meta.model';

describe('Component Tests', () => {
  describe('FileMeta Management Update Component', () => {
    let comp: FileMetaUpdateComponent;
    let fixture: ComponentFixture<FileMetaUpdateComponent>;
    let service: FileMetaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [FileMetaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FileMetaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileMetaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileMetaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileMeta(123);
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
        const entity = new FileMeta();
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
