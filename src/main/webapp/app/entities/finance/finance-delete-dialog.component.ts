import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinance } from 'app/shared/model/finance.model';
import { FinanceService } from './finance.service';

@Component({
  templateUrl: './finance-delete-dialog.component.html',
})
export class FinanceDeleteDialogComponent {
  finance?: IFinance;

  constructor(protected financeService: FinanceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.financeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('financeListModification');
      this.activeModal.close();
    });
  }
}
