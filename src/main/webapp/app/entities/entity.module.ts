import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer-info',
        loadChildren: () => import('./customer-info/customer-info.module').then(m => m.BookingCustomerInfoModule),
      },
      {
        path: 'employee-info',
        loadChildren: () => import('./employee-info/employee-info.module').then(m => m.BookingEmployeeInfoModule),
      },
      {
        path: 'room',
        loadChildren: () => import('./room/room.module').then(m => m.BookingRoomModule),
      },
      {
        path: 'booking',
        loadChildren: () => import('./booking/booking.module').then(m => m.BookingBookingModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.BookingAddressModule),
      },
      {
        path: 'finance',
        loadChildren: () => import('./finance/finance.module').then(m => m.BookingFinanceModule),
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.BookingInventoryModule),
      },
      {
        path: 'feed-back',
        loadChildren: () => import('./feed-back/feed-back.module').then(m => m.BookingFeedBackModule),
      },
      {
        path: 'file-meta',
        loadChildren: () => import('./file-meta/file-meta.module').then(m => m.BookingFileMetaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BookingEntityModule {}
