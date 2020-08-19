import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from './customer-info.service';

@Component({
  templateUrl: './customer-info-delete-dialog.component.html',
})
export class CustomerInfoDeleteDialogComponent {
  customerInfo?: ICustomerInfo;

  constructor(
    protected customerInfoService: CustomerInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('customerInfoListModification');
      this.activeModal.close();
    });
  }
}
