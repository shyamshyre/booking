import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICustomerInfo, CustomerInfo } from 'app/shared/model/customer-info.model';
import { CustomerInfoService } from './customer-info.service';
import { CustomerInfoComponent } from './customer-info.component';
import { CustomerInfoDetailComponent } from './customer-info-detail.component';
import { CustomerInfoUpdateComponent } from './customer-info-update.component';

@Injectable({ providedIn: 'root' })
export class CustomerInfoResolve implements Resolve<ICustomerInfo> {
  constructor(private service: CustomerInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((customerInfo: HttpResponse<CustomerInfo>) => {
          if (customerInfo.body) {
            return of(customerInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerInfo());
  }
}

export const customerInfoRoute: Routes = [
  {
    path: '',
    component: CustomerInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'bookingApp.customerInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerInfoDetailComponent,
    resolve: {
      customerInfo: CustomerInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.customerInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerInfoUpdateComponent,
    resolve: {
      customerInfo: CustomerInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.customerInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerInfoUpdateComponent,
    resolve: {
      customerInfo: CustomerInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bookingApp.customerInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
