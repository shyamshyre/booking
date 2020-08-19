import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { FeedBackComponent } from './feed-back.component';
import { FeedBackDetailComponent } from './feed-back-detail.component';
import { FeedBackUpdateComponent } from './feed-back-update.component';
import { FeedBackDeleteDialogComponent } from './feed-back-delete-dialog.component';
import { feedBackRoute } from './feed-back.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(feedBackRoute)],
  declarations: [FeedBackComponent, FeedBackDetailComponent, FeedBackUpdateComponent, FeedBackDeleteDialogComponent],
  entryComponents: [FeedBackDeleteDialogComponent],
})
export class BookingFeedBackModule {}
