import { Moment } from 'moment';
import { IBooking } from 'app/shared/model/booking.model';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { IUser } from 'app/core/user/user.model';

export interface IFileMeta {
  id?: number;
  name?: string;
  type?: string;
  size?: number;
  path?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  booking?: IBooking;
  customer?: ICustomerInfo;
  employee?: IEmployeeInfo;
  user?: IUser;
}

export class FileMeta implements IFileMeta {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public size?: number,
    public path?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public booking?: IBooking,
    public customer?: ICustomerInfo,
    public employee?: IEmployeeInfo,
    public user?: IUser
  ) {}
}
