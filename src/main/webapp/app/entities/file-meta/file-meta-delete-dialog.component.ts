import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileMeta } from 'app/shared/model/file-meta.model';
import { FileMetaService } from './file-meta.service';

@Component({
  templateUrl: './file-meta-delete-dialog.component.html',
})
export class FileMetaDeleteDialogComponent {
  fileMeta?: IFileMeta;

  constructor(protected fileMetaService: FileMetaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fileMetaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fileMetaListModification');
      this.activeModal.close();
    });
  }
}
