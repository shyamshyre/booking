import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBooking, Booking } from 'app/shared/model/booking.model';
import { BookingService } from './booking.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from 'app/entities/customer-info/customer-info.service';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from 'app/entities/employee-info/employee-info.service';

type SelectableEntity = IUser | ICustomerInfo | IEmployeeInfo;

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html',
})
export class BookingUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  customerinfos: ICustomerInfo[] = [];
  employeeinfos: IEmployeeInfo[] = [];

  editForm = this.fb.group({
    id: [],
    bookingType: [],
    noofPeople: [],
    comingFrom: [],
    visitPurpose: [],
    roomCategory: [],
    totalAmount: [],
    advance: [],
    balanceAmount: [],
    gst: [],
    bookingFrom: [],
    bookingTo: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    user: [],
    customer: [],
    employee: [],
  });

  constructor(
    protected bookingService: BookingService,
    protected userService: UserService,
    protected customerInfoService: CustomerInfoService,
    protected employeeInfoService: EmployeeInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ booking }) => {
      if (!booking.id) {
        const today = moment().startOf('day');
        booking.bookingFrom = today;
        booking.bookingTo = today;
        booking.createdDate = today;
        booking.updatedDate = today;
      }

      this.updateForm(booking);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.customerInfoService.query().subscribe((res: HttpResponse<ICustomerInfo[]>) => (this.customerinfos = res.body || []));

      this.employeeInfoService.query().subscribe((res: HttpResponse<IEmployeeInfo[]>) => (this.employeeinfos = res.body || []));
    });
  }

  updateForm(booking: IBooking): void {
    this.editForm.patchValue({
      id: booking.id,
      bookingType: booking.bookingType,
      noofPeople: booking.noofPeople,
      comingFrom: booking.comingFrom,
      visitPurpose: booking.visitPurpose,
      roomCategory: booking.roomCategory,
      totalAmount: booking.totalAmount,
      advance: booking.advance,
      balanceAmount: booking.balanceAmount,
      gst: booking.gst,
      bookingFrom: booking.bookingFrom ? booking.bookingFrom.format(DATE_TIME_FORMAT) : null,
      bookingTo: booking.bookingTo ? booking.bookingTo.format(DATE_TIME_FORMAT) : null,
      createdDate: booking.createdDate ? booking.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: booking.updatedDate ? booking.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: booking.createdBy,
      updatedBy: booking.updatedBy,
      user: booking.user,
      customer: booking.customer,
      employee: booking.employee,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  private createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id'])!.value,
      bookingType: this.editForm.get(['bookingType'])!.value,
      noofPeople: this.editForm.get(['noofPeople'])!.value,
      comingFrom: this.editForm.get(['comingFrom'])!.value,
      visitPurpose: this.editForm.get(['visitPurpose'])!.value,
      roomCategory: this.editForm.get(['roomCategory'])!.value,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      advance: this.editForm.get(['advance'])!.value,
      balanceAmount: this.editForm.get(['balanceAmount'])!.value,
      gst: this.editForm.get(['gst'])!.value,
      bookingFrom: this.editForm.get(['bookingFrom'])!.value
        ? moment(this.editForm.get(['bookingFrom'])!.value, DATE_TIME_FORMAT)
        : undefined,
      bookingTo: this.editForm.get(['bookingTo'])!.value ? moment(this.editForm.get(['bookingTo'])!.value, DATE_TIME_FORMAT) : undefined,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      user: this.editForm.get(['user'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      employee: this.editForm.get(['employee'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
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
