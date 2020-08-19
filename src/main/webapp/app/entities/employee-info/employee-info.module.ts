import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { EmployeeInfoComponent } from './employee-info.component';
import { EmployeeInfoDetailComponent } from './employee-info-detail.component';
import { EmployeeInfoUpdateComponent } from './employee-info-update.component';
import { EmployeeInfoDeleteDialogComponent } from './employee-info-delete-dialog.component';
import { employeeInfoRoute } from './employee-info.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(employeeInfoRoute)],
  declarations: [EmployeeInfoComponent, EmployeeInfoDetailComponent, EmployeeInfoUpdateComponent, EmployeeInfoDeleteDialogComponent],
  entryComponents: [EmployeeInfoDeleteDialogComponent],
})
export class BookingEmployeeInfoModule {}
