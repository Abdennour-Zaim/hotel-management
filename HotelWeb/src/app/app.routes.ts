import { Routes } from '@angular/router';
import {RegisterComponent} from './auth/components/register/register.component';
import {LoginComponent} from './auth/components/login/login.component';
import {DashboardComponent} from './modules/admin/components/dashboard/dashboard.component';
import {RoomsComponent} from './modules/customer/components/rooms/rooms.component';
import {UpdateRoomComponent} from './modules/admin/components/update-room/update-room.component';
import {ReservationsComponent} from './modules/admin/components/reservations/reservations.component';
import {ViewBookingsComponent} from './modules/customer/components/view-bookings/view-bookings.component';

export const routes: Routes = [
  {path:'register',component:RegisterComponent},
  {path:'login',component:LoginComponent},
  {path:'dashboard',component:DashboardComponent},
  {path:'rooms',component:RoomsComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'customer', loadChildren: () => import('./modules/customer/customer.module').then(m => m.CustomerModule) },
  { path: 'admin', loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule) },
  {path:'room/:id/edit',component:UpdateRoomComponent},
  {path:'reservations', component: ReservationsComponent},
  {path:'bookings',component:ViewBookingsComponent}
];
