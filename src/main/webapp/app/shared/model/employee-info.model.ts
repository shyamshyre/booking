import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { GenderType } from 'app/shared/model/enumerations/gender-type.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IEmployeeInfo {
  id?: number;
  name?: string;
  age?: number;
  gender?: GenderType;
  addressproof?: string;
  photo?: string;
  doj?: Moment;
  education?: string;
  referredby?: string;
  status?: Status;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  user?: IUser;
}

export class EmployeeInfo implements IEmployeeInfo {
  constructor(
    public id?: number,
    public name?: string,
    public age?: number,
    public gender?: GenderType,
    public addressproof?: string,
    public photo?: string,
    public doj?: Moment,
    public education?: string,
    public referredby?: string,
    public status?: Status,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public user?: IUser
  ) {}
}
