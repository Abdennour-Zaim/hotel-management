import { Component } from '@angular/core';
import {AdminService} from '../../admin-services/admin.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NgForOf, NgIf} from '@angular/common';
import {NzPaginationComponent} from 'ng-zorro-antd/pagination';
import {NzTagComponent} from 'ng-zorro-antd/tag';
import {NzIconDirective} from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [
    NzTableComponent,
    NgForOf,
    NzPaginationComponent,
    NgIf,
    NzTagComponent,
    NzIconDirective
  ],
  templateUrl: './reservations.component.html',
  styleUrl: './reservations.component.css'
})
export class ReservationsComponent {
  currentPage:any=1
  total:any
  reservations:any

  constructor(private adminService:AdminService,
              private message:NzMessageService) {
    this.getReservations();
  }

  getReservations(){
    this.adminService.getReservations(this.currentPage-1).subscribe(res=>{
      console.log(res)
      this.reservations = res.reservationDtoList();
      this.total = res.totalPages*5;
    })
  }
  pageIndexChange(value:any){
    this.currentPage = value;
    this.getReservations();
  }


  changeReservationStatus(bookingId:number, status: string) {

    this.adminService.changeReservationStatus(bookingId,status).subscribe(res=>{
      this.message.success("Reservation status changed successfully.",{nzDuration:5000});
      this.getReservations()
    },error => {
      this.message.error(
        `${error.error}`,{nzDuration:5000}
      )
    })
  }
}
