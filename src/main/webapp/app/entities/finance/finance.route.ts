import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFinance, Finance } from 'app/shared/model/finance.model';
import { FinanceService } from './finance.service';
import { FinanceComponent } from './finance.component';
import { FinanceDetailComponent } from './finance-detail.component';
import { FinanceUpdateComponent } from './finance-update.component';

@Injectable({ providedIn: 'root' })
export class FinanceResolve implements Resolve<IFinance> {
  constructor(private service: FinanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFinance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((finance: HttpResponse<Finance>) => {
          if (finance.body) {
            return of(finance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Finance());
  }
}

export const financeRoute: Routes = [
  {
    path: '',
    component: FinanceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bookingApp.finance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FinanceDetailComponent,
    resolve: {
      finance: FinanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.finance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FinanceUpdateComponent,
    resolve: {
      finance: FinanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.finance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FinanceUpdateComponent,
    resolve: {
      finance: FinanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.finance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
