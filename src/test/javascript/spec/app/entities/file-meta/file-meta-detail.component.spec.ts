import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingTestModule } from '../../../test.module';
import { FileMetaDetailComponent } from 'app/entities/file-meta/file-meta-detail.component';
import { FileMeta } from 'app/shared/model/file-meta.model';

describe('Component Tests', () => {
  describe('FileMeta Management Detail Component', () => {
    let comp: FileMetaDetailComponent;
    let fixture: ComponentFixture<FileMetaDetailComponent>;
    const route = ({ data: of({ fileMeta: new FileMeta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingTestModule],
        declarations: [FileMetaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FileMetaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileMetaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fileMeta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileMeta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
