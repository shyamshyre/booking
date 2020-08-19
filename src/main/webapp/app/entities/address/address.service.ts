import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAddress } from 'app/shared/model/address.model';

type EntityResponseType = HttpResponse<IAddress>;
type EntityArrayResponseType = HttpResponse<IAddress[]>;

@Injectable({ providedIn: 'root' })
export class AddressService {
  public resourceUrl = SERVER_API_URL + 'api/addresses';

  constructor(protected http: HttpClient) {}

  create(address: IAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(address);
    return this.http
      .post<IAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(address: IAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(address);
    return this.http
      .put<IAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAddress[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(address: IAddress): IAddress {
    const copy: IAddress = Object.assign({}, address, {
      createdDate: address.createdDate && address.createdDate.isValid() ? address.createdDate.toJSON() : undefined,
      updatedDate: address.updatedDate && address.updatedDate.isValid() ? address.updatedDate.toJSON() : undefined,
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
      res.body.forEach((address: IAddress) => {
        address.createdDate = address.createdDate ? moment(address.createdDate) : undefined;
        address.updatedDate = address.updatedDate ? moment(address.updatedDate) : undefined;
      });
    }
    return res;
  }
}
