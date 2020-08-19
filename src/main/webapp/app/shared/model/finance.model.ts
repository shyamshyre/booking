import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { IUser } from 'app/core/user/user.model';

export interface IFinance {
  id?: number;
  credited?: number;
  debited?: number;
  purpose?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  booking?: IBooking;
  user?: IUser;
}

export class Finance implements IFinance {
  constructor(
    public id?: number,
    public credited?: number,
    public debited?: number,
    public purpose?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public booking?: IBooking,
    public user?: IUser
  ) {}
}
