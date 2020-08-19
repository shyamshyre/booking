import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { CustomerInfoComponent } from './customer-info.component';
import { CustomerInfoDetailComponent } from './customer-info-detail.component';
import { CustomerInfoUpdateComponent } from './customer-info-update.component';
import { CustomerInfoDeleteDialogComponent } from './customer-info-delete-dialog.component';
import { customerInfoRoute } from './customer-info.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(customerInfoRoute)],
  declarations: [CustomerInfoComponent, CustomerInfoDetailComponent, CustomerInfoUpdateComponent, CustomerInfoDeleteDialogComponent],
  entryComponents: [CustomerInfoDeleteDialogComponent],
})
export class BookingCustomerInfoModule {}
