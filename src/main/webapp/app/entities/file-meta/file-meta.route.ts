import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFileMeta, FileMeta } from 'app/shared/model/file-meta.model';
import { FileMetaService } from './file-meta.service';
import { FileMetaComponent } from './file-meta.component';
import { FileMetaDetailComponent } from './file-meta-detail.component';
import { FileMetaUpdateComponent } from './file-meta-update.component';

@Injectable({ providedIn: 'root' })
export class FileMetaResolve implements Resolve<IFileMeta> {
  constructor(private service: FileMetaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFileMeta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fileMeta: HttpResponse<FileMeta>) => {
          if (fileMeta.body) {
            return of(fileMeta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FileMeta());
  }
}

export const fileMetaRoute: Routes = [
  {
    path: '',
    component: FileMetaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bookingApp.fileMeta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FileMetaDetailComponent,
    resolve: {
      fileMeta: FileMetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.fileMeta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FileMetaUpdateComponent,
    resolve: {
      fileMeta: FileMetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.fileMeta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FileMetaUpdateComponent,
    resolve: {
      fileMeta: FileMetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.fileMeta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
