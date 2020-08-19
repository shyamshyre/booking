import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from 'app/entities/customer-info/customer-info.service';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from 'app/entities/employee-info/employee-info.service';

type SelectableEntity = ICustomerInfo | IEmployeeInfo;

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomerInfo[] = [];
  employees: IEmployeeInfo[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    dno: [],
    locality: [],
    streetName: [],
    district: [],
    city: [],
    pincode: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    customer: [],
    employee: [],
  });

  constructor(
    protected addressService: AddressService,
    protected customerInfoService: CustomerInfoService,
    protected employeeInfoService: EmployeeInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      if (!address.id) {
        const today = moment().startOf('day');
        address.createdDate = today;
        address.updatedDate = today;
      }

      this.updateForm(address);

      this.customerInfoService
        .query({ filter: 'address-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerInfo[]) => {
          if (!address.customer || !address.customer.id) {
            this.customers = resBody;
          } else {
            this.customerInfoService
              .find(address.customer.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerInfo[]) => (this.customers = concatRes));
          }
        });

      this.employeeInfoService
        .query({ filter: 'address-is-null' })
        .pipe(
          map((res: HttpResponse<IEmployeeInfo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployeeInfo[]) => {
          if (!address.employee || !address.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeInfoService
              .find(address.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployeeInfo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployeeInfo[]) => (this.employees = concatRes));
          }
        });
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      name: address.name,
      dno: address.dno,
      locality: address.locality,
      streetName: address.streetName,
      district: address.district,
      city: address.city,
      pincode: address.pincode,
      createdDate: address.createdDate ? address.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: address.updatedDate ? address.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: address.createdBy,
      updatedBy: address.updatedBy,
      customer: address.customer,
      employee: address.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      dno: this.editForm.get(['dno'])!.value,
      locality: this.editForm.get(['locality'])!.value,
      streetName: this.editForm.get(['streetName'])!.value,
      district: this.editForm.get(['district'])!.value,
      city: this.editForm.get(['city'])!.value,
      pincode: this.editForm.get(['pincode'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
