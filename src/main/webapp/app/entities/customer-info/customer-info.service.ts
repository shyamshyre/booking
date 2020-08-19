import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerInfo } from 'app/shared/model/customer-info.model';

type EntityResponseType = HttpResponse<ICustomerInfo>;
type EntityArrayResponseType = HttpResponse<ICustomerInfo[]>;

@Injectable({ providedIn: 'root' })
export class CustomerInfoService {
  public resourceUrl = SERVER_API_URL + 'api/customer-infos';

  constructor(protected http: HttpClient) {}

  create(customerInfo: ICustomerInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerInfo);
    return this.http
      .post<ICustomerInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(customerInfo: ICustomerInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(customerInfo);
    return this.http
      .put<ICustomerInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICustomerInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICustomerInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(customerInfo: ICustomerInfo): ICustomerInfo {
    const copy: ICustomerInfo = Object.assign({}, customerInfo, {
      createdDate: customerInfo.createdDate && customerInfo.createdDate.isValid() ? customerInfo.createdDate.toJSON() : undefined,
      updatedDate: customerInfo.updatedDate && customerInfo.updatedDate.isValid() ? customerInfo.updatedDate.toJSON() : undefined,
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
      res.body.forEach((customerInfo: ICustomerInfo) => {
        customerInfo.createdDate = customerInfo.createdDate ? moment(customerInfo.createdDate) : undefined;
        customerInfo.updatedDate = customerInfo.updatedDate ? moment(customerInfo.updatedDate) : undefined;
      });
    }
    return res;
  }
}
