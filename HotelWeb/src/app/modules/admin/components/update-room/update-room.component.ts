import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {AdminService} from '../../admin-services/admin.service';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzColDirective} from 'ng-zorro-antd/grid';
import {NzFormControlComponent, NzFormItemComponent} from 'ng-zorro-antd/form';
import {NzInputDirective} from 'ng-zorro-antd/input';

@Component({
  selector: 'app-update-room',
  standalone: true,
  imports: [
    FormsModule,
    NzButtonComponent,
    NzColDirective,
    NzFormControlComponent,
    NzFormItemComponent,
    NzInputDirective,
    ReactiveFormsModule
  ],
  templateUrl: './update-room.component.html',
  styleUrl: './update-room.component.css'
})
export class UpdateRoomComponent {

  updateRoomForm: FormGroup;
  id=this.activatedRoute.snapshot.params['id'];
  constructor(private fb: FormBuilder,
              private router: Router,
              private message:NzMessageService,
              private adminService: AdminService,
              private activatedRoute: ActivatedRoute) {
    this.updateRoomForm = fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      price: ['', Validators.required],
    })
    this.getRoomById()

  }

  submitForm() {
    this.adminService.updateRoomDetails(this.id,this.updateRoomForm.value).subscribe(req=>{
      this.message.success("room updated successfully.",{nzDuration:5000})
      this.router.navigateByUrl('/admin/dashboard')
    },error => {
      this.message.error(`${error.error}`,{nzDuration:5000})
    })
  }
  getRoomById() {
    this.adminService.getRoomById(this.id).subscribe(res=>{
      this.updateRoomForm.patchValue(res)
    },error => {
      this.message.error(`${error.error}`, {nzDuration:5000});
    })
  }
}
