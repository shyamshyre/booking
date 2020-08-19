import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFinance } from 'app/shared/model/finance.model';

type EntityResponseType = HttpResponse<IFinance>;
type EntityArrayResponseType = HttpResponse<IFinance[]>;

@Injectable({ providedIn: 'root' })
export class FinanceService {
  public resourceUrl = SERVER_API_URL + 'api/finances';

  constructor(protected http: HttpClient) {}

  create(finance: IFinance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finance);
    return this.http
      .post<IFinance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(finance: IFinance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finance);
    return this.http
      .put<IFinance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFinance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFinance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(finance: IFinance): IFinance {
    const copy: IFinance = Object.assign({}, finance, {
      createdDate: finance.createdDate && finance.createdDate.isValid() ? finance.createdDate.toJSON() : undefined,
      updatedDate: finance.updatedDate && finance.updatedDate.isValid() ? finance.updatedDate.toJSON() : undefined,
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
      res.body.forEach((finance: IFinance) => {
        finance.createdDate = finance.createdDate ? moment(finance.createdDate) : undefined;
        finance.updatedDate = finance.updatedDate ? moment(finance.updatedDate) : undefined;
      });
    }
    return res;
  }
}
