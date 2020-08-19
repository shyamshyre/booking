import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { MStatus } from 'app/shared/model/enumerations/m-status.model';

export interface ICustomerInfo {
  id?: number;
  name?: string;
  photo?: string;
  age?: number;
  phno?: number;
  email?: string;
  gender?: GenderType;
  status?: MStatus;
  addressproof?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  user?: IUser;
}

export class CustomerInfo implements ICustomerInfo {
  constructor(
    public id?: number,
    public name?: string,
    public photo?: string,
    public age?: number,
    public phno?: number,
    public email?: string,
    public gender?: GenderType,
    public status?: MStatus,
    public addressproof?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public user?: IUser
  ) {}
}
