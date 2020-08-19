import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFinance, Finance } from 'app/shared/model/finance.model';
import { FinanceService } from './finance.service';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking/booking.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IBooking | IUser;

@Component({
  selector: 'jhi-finance-update',
  templateUrl: './finance-update.component.html',
})
export class FinanceUpdateComponent implements OnInit {
  isSaving = false;
  bookings: IBooking[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    credited: [],
    debited: [],
    purpose: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    booking: [],
    user: [],
  });

  constructor(
    protected financeService: FinanceService,
    protected bookingService: BookingService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finance }) => {
      if (!finance.id) {
        const today = moment().startOf('day');
        finance.createdDate = today;
        finance.updatedDate = today;
      }

      this.updateForm(finance);

      this.bookingService.query().subscribe((res: HttpResponse<IBooking[]>) => (this.bookings = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(finance: IFinance): void {
    this.editForm.patchValue({
      id: finance.id,
      credited: finance.credited,
      debited: finance.debited,
      purpose: finance.purpose,
      createdDate: finance.createdDate ? finance.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: finance.updatedDate ? finance.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: finance.createdBy,
      updatedBy: finance.updatedBy,
      booking: finance.booking,
      user: finance.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const finance = this.createFromForm();
    if (finance.id !== undefined) {
      this.subscribeToSaveResponse(this.financeService.update(finance));
    } else {
      this.subscribeToSaveResponse(this.financeService.create(finance));
    }
  }

  private createFromForm(): IFinance {
    return {
      ...new Finance(),
      id: this.editForm.get(['id'])!.value,
      credited: this.editForm.get(['credited'])!.value,
      debited: this.editForm.get(['debited'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      updatedDate: this.editForm.get(['updatedDate'])!.value
        ? moment(this.editForm.get(['updatedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
      booking: this.editForm.get(['booking'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinance>>): void {
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
