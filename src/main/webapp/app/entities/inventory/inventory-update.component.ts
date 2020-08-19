import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-inventory-update',
  templateUrl: './inventory-update.component.html',
})
export class InventoryUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    purchaseDate: [],
    itemdescription: [],
    quantity: [],
    available: [],
    itemcat: [],
    purchaseAmount: [],
    purpose: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    user: [],
  });

  constructor(
    protected inventoryService: InventoryService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventory }) => {
      if (!inventory.id) {
        const today = moment().startOf('day');
        inventory.createdDate = today;
        inventory.updatedDate = today;
      }

      this.updateForm(inventory);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(inventory: IInventory): void {
    this.editForm.patchValue({
      id: inventory.id,
      purchaseDate: inventory.purchaseDate,
      itemdescription: inventory.itemdescription,
      quantity: inventory.quantity,
      available: inventory.available,
      itemcat: inventory.itemcat,
      purchaseAmount: inventory.purchaseAmount,
      purpose: inventory.purpose,
      createdDate: inventory.createdDate ? inventory.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: inventory.updatedDate ? inventory.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: inventory.createdBy,
      updatedBy: inventory.updatedBy,
      user: inventory.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  private createFromForm(): IInventory {
    return {
      ...new Inventory(),
      id: this.editForm.get(['id'])!.value,
      purchaseDate: this.editForm.get(['purchaseDate'])!.value,
      itemdescription: this.editForm.get(['itemdescription'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      available: this.editForm.get(['available'])!.value,
      itemcat: this.editForm.get(['itemcat'])!.value,
      purchaseAmount: this.editForm.get(['purchaseAmount'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
