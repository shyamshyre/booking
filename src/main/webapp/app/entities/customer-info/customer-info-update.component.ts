import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICustomerInfo, CustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from './customer-info.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-customer-info-update',
  templateUrl: './customer-info-update.component.html',
})
export class CustomerInfoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    photo: [],
    age: [],
    phno: [],
    email: [],
    gender: [],
    status: [],
    addressproof: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    user: [],
  });

  constructor(
    protected customerInfoService: CustomerInfoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerInfo }) => {
      if (!customerInfo.id) {
        const today = moment().startOf('day');
        customerInfo.createdDate = today;
        customerInfo.updatedDate = today;
      }

      this.updateForm(customerInfo);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(customerInfo: ICustomerInfo): void {
    this.editForm.patchValue({
      id: customerInfo.id,
      name: customerInfo.name,
      photo: customerInfo.photo,
      age: customerInfo.age,
      phno: customerInfo.phno,
      email: customerInfo.email,
      gender: customerInfo.gender,
      status: customerInfo.status,
      addressproof: customerInfo.addressproof,
      createdDate: customerInfo.createdDate ? customerInfo.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: customerInfo.updatedDate ? customerInfo.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: customerInfo.createdBy,
      updatedBy: customerInfo.updatedBy,
      user: customerInfo.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerInfo = this.createFromForm();
    if (customerInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.customerInfoService.update(customerInfo));
    } else {
      this.subscribeToSaveResponse(this.customerInfoService.create(customerInfo));
    }
  }

  private createFromForm(): ICustomerInfo {
    return {
      ...new CustomerInfo(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      age: this.editForm.get(['age'])!.value,
      phno: this.editForm.get(['phno'])!.value,
      email: this.editForm.get(['email'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      status: this.editForm.get(['status'])!.value,
      addressproof: this.editForm.get(['addressproof'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerInfo>>): void {
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
