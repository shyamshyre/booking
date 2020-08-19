import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IInventory {
  id?: number;
  purchaseDate?: number;
  itemdescription?: string;
  quantity?: number;
  available?: number;
  itemcat?: string;
  purchaseAmount?: number;
  purpose?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  createdBy?: number;
  updatedBy?: number;
  user?: IUser;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public purchaseDate?: number,
    public itemdescription?: string,
    public quantity?: number,
    public available?: number,
    public itemcat?: string,
    public purchaseAmount?: number,
    public purpose?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public createdBy?: number,
    public updatedBy?: number,
    public user?: IUser
  ) {}
}
