import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmployeeInfo } from 'app/shared/model/employee-info.model';

type EntityResponseType = HttpResponse<IEmployeeInfo>;
type EntityArrayResponseType = HttpResponse<IEmployeeInfo[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeInfoService {
  public resourceUrl = SERVER_API_URL + 'api/employee-infos';

  constructor(protected http: HttpClient) {}

  create(employeeInfo: IEmployeeInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeInfo);
    return this.http
      .post<IEmployeeInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeInfo: IEmployeeInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeInfo);
    return this.http
      .put<IEmployeeInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeeInfo: IEmployeeInfo): IEmployeeInfo {
    const copy: IEmployeeInfo = Object.assign({}, employeeInfo, {
      doj: employeeInfo.doj && employeeInfo.doj.isValid() ? employeeInfo.doj.toJSON() : undefined,
      createdDate: employeeInfo.createdDate && employeeInfo.createdDate.isValid() ? employeeInfo.createdDate.toJSON() : undefined,
      updatedDate: employeeInfo.updatedDate && employeeInfo.updatedDate.isValid() ? employeeInfo.updatedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.doj = res.body.doj ? moment(res.body.doj) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeeInfo: IEmployeeInfo) => {
        employeeInfo.doj = employeeInfo.doj ? moment(employeeInfo.doj) : undefined;
        employeeInfo.createdDate = employeeInfo.createdDate ? moment(employeeInfo.createdDate) : undefined;
        employeeInfo.updatedDate = employeeInfo.updatedDate ? moment(employeeInfo.updatedDate) : undefined;
      });
    }
    return res;
  }
}
