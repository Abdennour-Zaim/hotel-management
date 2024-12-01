import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CustomerComponent} from './customer/customer.component';
import {DemoNgZorroAndModule} from '../../DemoNgZorroAndModule';
import {RoomsComponent} from './components/rooms/rooms.component';
import {ViewBookingsComponent} from './components/view-bookings/view-bookings.component';

const routes: Routes = [
  { path: '', component: CustomerComponent },  // Default route for this module
  {path:'rooms', component: RoomsComponent},
  {path:'bookings',component:ViewBookingsComponent}
];

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild([]),
    DemoNgZorroAndModule
  ]
})
export class CustomerModule { }
