import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {AdminComponent} from './admin/admin.component';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {RoomsComponent} from '../customer/components/rooms/rooms.component';
import {PostRoomComponent} from './components/post-room/post-room.component';
import {UpdateRoomComponent} from './components/update-room/update-room.component';
import {ReservationsComponent} from './components/reservations/reservations.component';



const routes: Routes = [
  { path: '', component: AdminComponent },  // Default route for this module
  {path: 'dashboard', component: DashboardComponent},
  {path:'rooms', component: PostRoomComponent},
  {path:'room/:id/edit', component: UpdateRoomComponent},
  {path:'reservations', component: ReservationsComponent},
];
@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)  // Use RouterModule.forChild for feature module routing

  ]
})
export class AdminModule { }
