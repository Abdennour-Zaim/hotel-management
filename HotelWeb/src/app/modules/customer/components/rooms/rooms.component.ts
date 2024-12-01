import { Component } from '@angular/core';
import {AdminService} from '../../../admin/admin-services/admin.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalService} from 'ng-zorro-antd/modal';
import {CustomerService} from '../../service/customer.service';
import {NgForOf} from '@angular/common';
import {NzAvatarComponent} from 'ng-zorro-antd/avatar';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzCardComponent, NzCardMetaComponent} from 'ng-zorro-antd/card';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzPaginationComponent} from 'ng-zorro-antd/pagination';
import {NzSkeletonComponent} from 'ng-zorro-antd/skeleton';
import {RouterLink} from '@angular/router';
import {NzDatePickerComponent} from 'ng-zorro-antd/date-picker';
import {FormsModule} from '@angular/forms';
import {UserStorageService} from '../../../../auth/services/storage/user-storage.service';

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [
    NgForOf,
    NzAvatarComponent,
    NzButtonComponent,
    NzCardComponent,
    NzCardMetaComponent,
    NzIconDirective,
    NzPaginationComponent,
    NzSkeletonComponent,
    RouterLink,
    NzModalComponent,
    NzDatePickerComponent,
    NzModalContentDirective,
    FormsModule
  ],
  templateUrl: './rooms.component.html',
  styleUrl: './rooms.component.css'
})
export class RoomsComponent {
  currentPage=1
  rooms=[]
  total:any
  loading=false
  constructor(private customerService: CustomerService,
              private message: NzMessageService,
              private modalService: NzModalService,) {
    this.getRooms()
  }

  getRooms(){
    this.customerService.getRooms(this.currentPage).subscribe(res=>{
      console.log(res)
      this.rooms=res.roomDtoList;
      this.total=res.totalPages*1;
    })
  }

  pageIndexChange(value: any) {
    this.currentPage = value;
    this.getRooms();
  }

  isVisibleMiddle=false
  date:Date[]=[]
  checkInDate:Date
  checkOutDate:Date
  id:number
  onChange(result:Date[]){
    if(result.length===2){
      this.checkInDate=result[0]
      this.checkOutDate=result[1]
    }
  }
  handleCancelMiddle(){
    this.isVisibleMiddle=false
  }
  handleOkMiddle():void{
    const obj= {
      userId: UserStorageService.getUserId(),
      roomId:this.id,
      checkInDate:this.checkInDate,
      checkOutDate:this.checkOutDate,
    }
    this.customerService.bookRoom(obj).subscribe(res=>{
      this.message.success('Request submitted for approval !',{nzDuration:5000})
      this.isVisibleMiddle=false},
      error => {
      this.message.error(`${error.error}`,{nzDuration:5000})
      })
  }
  showModalMiddle(id:number){
    this.id=id,
      this.isVisibleMiddle=true
  }
}
