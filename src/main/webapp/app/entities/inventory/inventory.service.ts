import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventory } from 'app/shared/model/inventory.model';

type EntityResponseType = HttpResponse<IInventory>;
type EntityArrayResponseType = HttpResponse<IInventory[]>;

@Injectable({ providedIn: 'root' })
export class InventoryService {
  public resourceUrl = SERVER_API_URL + 'api/inventories';

  constructor(protected http: HttpClient) {}

  create(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .post<IInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventory: IInventory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventory);
    return this.http
      .put<IInventory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventory: IInventory): IInventory {
    const copy: IInventory = Object.assign({}, inventory, {
      createdDate: inventory.createdDate && inventory.createdDate.isValid() ? inventory.createdDate.toJSON() : undefined,
      updatedDate: inventory.updatedDate && inventory.updatedDate.isValid() ? inventory.updatedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventory: IInventory) => {
        inventory.createdDate = inventory.createdDate ? moment(inventory.createdDate) : undefined;
        inventory.updatedDate = inventory.updatedDate ? moment(inventory.updatedDate) : undefined;
      });
    }
    return res;
  }
}
