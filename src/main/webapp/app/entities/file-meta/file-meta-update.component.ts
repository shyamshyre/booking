import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFileMeta, FileMeta } from 'app/shared/model/file-meta.model';
import { FileMetaService } from './file-meta.service';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking/booking.service';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from 'app/entities/customer-info/customer-info.service';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from 'app/entities/employee-info/employee-info.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IBooking | ICustomerInfo | IEmployeeInfo | IUser;

@Component({
  selector: 'jhi-file-meta-update',
  templateUrl: './file-meta-update.component.html',
})
export class FileMetaUpdateComponent implements OnInit {
  isSaving = false;
  bookings: IBooking[] = [];
  customerinfos: ICustomerInfo[] = [];
  employeeinfos: IEmployeeInfo[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    type: [],
    size: [],
    path: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    booking: [],
    customer: [],
    employee: [],
    user: [],
  });

  constructor(
    protected fileMetaService: FileMetaService,
    protected bookingService: BookingService,
    protected customerInfoService: CustomerInfoService,
    protected employeeInfoService: EmployeeInfoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileMeta }) => {
      if (!fileMeta.id) {
        const today = moment().startOf('day');
        fileMeta.createdDate = today;
        fileMeta.updatedDate = today;
      }

      this.updateForm(fileMeta);

      this.bookingService.query().subscribe((res: HttpResponse<IBooking[]>) => (this.bookings = res.body || []));

      this.customerInfoService.query().subscribe((res: HttpResponse<ICustomerInfo[]>) => (this.customerinfos = res.body || []));

      this.employeeInfoService.query().subscribe((res: HttpResponse<IEmployeeInfo[]>) => (this.employeeinfos = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(fileMeta: IFileMeta): void {
    this.editForm.patchValue({
      id: fileMeta.id,
      name: fileMeta.name,
      type: fileMeta.type,
      size: fileMeta.size,
      path: fileMeta.path,
      createdDate: fileMeta.createdDate ? fileMeta.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: fileMeta.updatedDate ? fileMeta.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: fileMeta.createdBy,
      updatedBy: fileMeta.updatedBy,
      booking: fileMeta.booking,
      customer: fileMeta.customer,
      employee: fileMeta.employee,
      user: fileMeta.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileMeta = this.createFromForm();
    if (fileMeta.id !== undefined) {
      this.subscribeToSaveResponse(this.fileMetaService.update(fileMeta));
    } else {
      this.subscribeToSaveResponse(this.fileMetaService.create(fileMeta));
    }
  }

  private createFromForm(): IFileMeta {
    return {
      ...new FileMeta(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      size: this.editForm.get(['size'])!.value,
      path: this.editForm.get(['path'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      booking: this.editForm.get(['booking'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileMeta>>): void {
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
