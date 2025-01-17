import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { RoomComponent } from './room.component';
import { RoomDetailComponent } from './room-detail.component';
import { RoomUpdateComponent } from './room-update.component';
import { RoomDeleteDialogComponent } from './room-delete-dialog.component';
import { roomRoute } from './room.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(roomRoute)],
  declarations: [RoomComponent, RoomDetailComponent, RoomUpdateComponent, RoomDeleteDialogComponent],
  entryComponents: [RoomDeleteDialogComponent],
})
export class BookingRoomModule {}
