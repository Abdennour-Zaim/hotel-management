import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzFormControlComponent, NzFormItemComponent} from 'ng-zorro-antd/form';
import {NzColDirective} from 'ng-zorro-antd/grid';
import {NzInputDirective} from 'ng-zorro-antd/input';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {AdminService} from '../../admin-services/admin.service';

@Component({
  selector: 'app-post-room',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzFormControlComponent,
    NzFormItemComponent,
    NzColDirective,
    NzInputDirective,
    NzButtonComponent
  ],
  templateUrl: './post-room.component.html',
  styleUrl: './post-room.component.css'
})
export class PostRoomComponent {
  roomDetailsForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private message:NzMessageService,
              private adminService: AdminService,) {
    this.roomDetailsForm = fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      price: ['', Validators.required],
    })
  }

  submitForm() {
    this.adminService.postRoomDetails(this.roomDetailsForm.value).subscribe(res=>{
      this.message.success('Room Posted Successfully!',{nzDuration:5000});
      this.router.navigateByUrl('/admin/dashboard')
    }, error => {
      this.message.error(`${error.error}`,{nzDuration:5000});
    })
  }
}
