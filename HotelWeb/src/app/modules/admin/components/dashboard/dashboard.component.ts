import { Component } from '@angular/core';
import {AdminService} from '../../admin-services/admin.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {RouterLink} from '@angular/router';
import {NzCardComponent, NzCardMetaComponent} from 'ng-zorro-antd/card';
import {NzSkeletonComponent} from 'ng-zorro-antd/skeleton';
import {NzAvatarComponent} from 'ng-zorro-antd/avatar';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NgForOf} from '@angular/common';
import {NzPaginationComponent} from 'ng-zorro-antd/pagination';
import {NzModalService} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    NzButtonComponent,
    RouterLink,
    NzCardComponent,
    NzSkeletonComponent,
    NzCardMetaComponent,
    NzAvatarComponent,
    NzIconDirective,
    NgForOf,
    NzPaginationComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

currentPage=1
  rooms=[]
  total:any
  loading=false
  constructor(private adminService: AdminService,
              private message: NzMessageService,
              private modalService: NzModalService,) {
  this.getRooms()
  }

  getRooms(){
  this.adminService.getRooms(this.currentPage).subscribe(res=>{
    console.log(res)
    this.rooms=res.roomDtoList;
    this.total=res.totalPages*1;
  })
  }

  pageIndexChange(value: any) {
  this.currentPage = value;
  this.getRooms();
  }

  showConfirm(roomId:number) {
  this.modalService.confirm({
    nzTitle:"confirm",
    nzContent:"Do you really want to delete this room?",
    nzOkText:"Delete",
    nzCancelText:"Cancel",
    nzOnOk:()=>this.deleteRoom(roomId)
  })
  }
  deleteRoom(roomId:number){
  this.adminService.deleteRoom(roomId).subscribe(res=>{
    this.message.success("Room deleted successfully.",{nzDuration:5000});
    this.getRooms()
  },error => {this.message.error(`${error.error}`,{nzDuration:5000});})
  }
}
