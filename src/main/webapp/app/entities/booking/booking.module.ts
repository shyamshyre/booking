import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { BookingComponent } from './booking.component';
import { BookingDetailComponent } from './booking-detail.component';
import { BookingUpdateComponent } from './booking-update.component';
import { BookingDeleteDialogComponent } from './booking-delete-dialog.component';
import { bookingRoute } from './booking.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(bookingRoute)],
  declarations: [BookingComponent, BookingDetailComponent, BookingUpdateComponent, BookingDeleteDialogComponent],
  entryComponents: [BookingDeleteDialogComponent],
})
export class BookingBookingModule {}
