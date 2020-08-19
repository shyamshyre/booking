import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { FileMetaComponent } from './file-meta.component';
import { FileMetaDetailComponent } from './file-meta-detail.component';
import { FileMetaUpdateComponent } from './file-meta-update.component';
import { FileMetaDeleteDialogComponent } from './file-meta-delete-dialog.component';
import { fileMetaRoute } from './file-meta.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(fileMetaRoute)],
  declarations: [FileMetaComponent, FileMetaDetailComponent, FileMetaUpdateComponent, FileMetaDeleteDialogComponent],
  entryComponents: [FileMetaDeleteDialogComponent],
})
export class BookingFileMetaModule {}
