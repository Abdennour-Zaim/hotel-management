import { Component } from '@angular/core';
import {CustomerService} from '../../service/customer.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NgForOf, NgIf} from '@angular/common';
import {NzTagComponent} from 'ng-zorro-antd/tag';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzPaginationComponent} from 'ng-zorro-antd/pagination';

@Component({
  selector: 'app-view-bookings',
  standalone: true,
  imports: [
    NzTableComponent,
    NgForOf,
    NgIf,
    NzTagComponent,
    NzIconDirective,
    NzPaginationComponent
  ],
  templateUrl: './view-bookings.component.html',
  styleUrl: './view-bookings.component.css'
})
export class ViewBookingsComponent {

  total:any
  bookings:any

  currentPage:any=1
  constructor(private customerService:CustomerService,
              private message:NzMessageService) {
    this.getBookings()
  }

  private getBookings() {
    this.customerService.getMyBookings(this.currentPage-1).subscribe(res=>{
      console.log(res)
      this.bookings = res.reservationDtoList;
      this.total = res.totalPages*5;
    },error => {
      this.message.error(
        `${error.error}`,{nzDuration:5000}
      )
    })

  }
  pageIndexChange(value:any) {
    this.currentPage = value;
    this.getBookings();
  }
}
