import { Moment } from 'moment';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';

export interface IAddress {
  id?: number;
  name?: string;
  dno?: string;
  locality?: string;
  streetName?: string;
  district?: string;
  city?: string;
  pincode?: number;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  customer?: ICustomerInfo;
  employee?: IEmployeeInfo;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public name?: string,
    public dno?: string,
    public locality?: string,
    public streetName?: string,
    public district?: string,
    public city?: string,
    public pincode?: number,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public customer?: ICustomerInfo,
    public employee?: IEmployeeInfo
  ) {}
}
