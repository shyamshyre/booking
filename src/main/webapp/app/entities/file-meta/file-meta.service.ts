import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFileMeta } from 'app/shared/model/file-meta.model';

type EntityResponseType = HttpResponse<IFileMeta>;
type EntityArrayResponseType = HttpResponse<IFileMeta[]>;

@Injectable({ providedIn: 'root' })
export class FileMetaService {
  public resourceUrl = SERVER_API_URL + 'api/file-metas';

  constructor(protected http: HttpClient) {}

  create(fileMeta: IFileMeta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileMeta);
    return this.http
      .post<IFileMeta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fileMeta: IFileMeta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileMeta);
    return this.http
      .put<IFileMeta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFileMeta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileMeta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fileMeta: IFileMeta): IFileMeta {
    const copy: IFileMeta = Object.assign({}, fileMeta, {
      createdDate: fileMeta.createdDate && fileMeta.createdDate.isValid() ? fileMeta.createdDate.toJSON() : undefined,
      updatedDate: fileMeta.updatedDate && fileMeta.updatedDate.isValid() ? fileMeta.updatedDate.toJSON() : undefined,
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
      res.body.forEach((fileMeta: IFileMeta) => {
        fileMeta.createdDate = fileMeta.createdDate ? moment(fileMeta.createdDate) : undefined;
        fileMeta.updatedDate = fileMeta.updatedDate ? moment(fileMeta.updatedDate) : undefined;
      });
    }
    return res;
  }
}
