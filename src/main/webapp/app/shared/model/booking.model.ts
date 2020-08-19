import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { BookingType } from 'app/shared/model/enumerations/booking-type.model';
import { RoomType } from 'app/shared/model/enumerations/room-type.model';

export interface IBooking {
  id?: number;
  bookingType?: BookingType;
  noofPeople?: number;
  comingFrom?: string;
  visitPurpose?: string;
  roomCategory?: RoomType;
  totalAmount?: number;
  advance?: number;
  balanceAmount?: number;
  gst?: number;
  bookingFrom?: Moment;
  bookingTo?: Moment;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  user?: IUser;
  customer?: ICustomerInfo;
  employee?: IEmployeeInfo;
}

export class Booking implements IBooking {
  constructor(
    public id?: number,
    public bookingType?: BookingType,
    public noofPeople?: number,
    public comingFrom?: string,
    public visitPurpose?: string,
    public roomCategory?: RoomType,
    public totalAmount?: number,
    public advance?: number,
    public balanceAmount?: number,
    public gst?: number,
    public bookingFrom?: Moment,
    public bookingTo?: Moment,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public user?: IUser,
    public customer?: ICustomerInfo,
    public employee?: IEmployeeInfo
  ) {}
}
