import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { IUser } from 'app/core/user/user.model';
import { RoomType } from 'app/shared/model/enumerations/room-type.model';
import { RoomState } from 'app/shared/model/enumerations/room-state.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IRoom {
  id?: number;
  rooomno?: number;
  roomType?: RoomType;
  rstatus?: RoomState;
  cost?: number;
  status?: Status;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  booking?: IBooking;
  user?: IUser;
}

export class Room implements IRoom {
  constructor(
    public id?: number,
    public rooomno?: number,
    public roomType?: RoomType,
    public rstatus?: RoomState,
    public cost?: number,
    public status?: Status,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public booking?: IBooking,
    public user?: IUser
  ) {}
}
