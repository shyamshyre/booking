import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BookingSharedModule } from 'app/shared/shared.module';
import { FinanceComponent } from './finance.component';
import { FinanceDetailComponent } from './finance-detail.component';
import { FinanceUpdateComponent } from './finance-update.component';
import { FinanceDeleteDialogComponent } from './finance-delete-dialog.component';
import { financeRoute } from './finance.route';

@NgModule({
  imports: [BookingSharedModule, RouterModule.forChild(financeRoute)],
  declarations: [FinanceComponent, FinanceDetailComponent, FinanceUpdateComponent, FinanceDeleteDialogComponent],
  entryComponents: [FinanceDeleteDialogComponent],
})
export class BookingFinanceModule {}
