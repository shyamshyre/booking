import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRoom, Room } from 'app/shared/model/room.model';
import { RoomService } from './room.service';
import { IBooking } from 'app/shared/model/booking.model';
import { BookingService } from 'app/entities/booking/booking.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IBooking | IUser;

@Component({
  selector: 'jhi-room-update',
  templateUrl: './room-update.component.html',
})
export class RoomUpdateComponent implements OnInit {
  isSaving = false;
  bookings: IBooking[] = [];
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    rooomno: [],
    roomType: [],
    rstatus: [],
    cost: [],
    status: [],
    createdDate: [],
    updatedDate: [],
    createdBy: [],
    updatedBy: [],
    booking: [],
    user: [],
  });

  constructor(
    protected roomService: RoomService,
    protected bookingService: BookingService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ room }) => {
      if (!room.id) {
        const today = moment().startOf('day');
        room.createdDate = today;
        room.updatedDate = today;
      }

      this.updateForm(room);

      this.bookingService.query().subscribe((res: HttpResponse<IBooking[]>) => (this.bookings = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(room: IRoom): void {
    this.editForm.patchValue({
      id: room.id,
      rooomno: room.rooomno,
      roomType: room.roomType,
      rstatus: room.rstatus,
      cost: room.cost,
      status: room.status,
      createdDate: room.createdDate ? room.createdDate.format(DATE_TIME_FORMAT) : null,
      updatedDate: room.updatedDate ? room.updatedDate.format(DATE_TIME_FORMAT) : null,
      createdBy: room.createdBy,
      updatedBy: room.updatedBy,
      booking: room.booking,
      user: room.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const room = this.createFromForm();
    if (room.id !== undefined) {
      this.subscribeToSaveResponse(this.roomService.update(room));
    } else {
      this.subscribeToSaveResponse(this.roomService.create(room));
    }
  }

  private createFromForm(): IRoom {
    return {
      ...new Room(),
      id: this.editForm.get(['id'])!.value,
      rooomno: this.editForm.get(['rooomno'])!.value,
      roomType: this.editForm.get(['roomType'])!.value,
      rstatus: this.editForm.get(['rstatus'])!.value,
      cost: this.editForm.get(['cost'])!.value,
      status: this.editForm.get(['status'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoom>>): void {
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
