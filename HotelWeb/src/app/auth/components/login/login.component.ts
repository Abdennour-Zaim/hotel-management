import { Component } from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../services/auth/auth.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {Router} from '@angular/router';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzFormControlComponent, NzFormDirective, NzFormItemComponent} from 'ng-zorro-antd/form';
import {NzInputDirective, NzInputGroupComponent} from 'ng-zorro-antd/input';
import {UserStorageService} from '../../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NzButtonComponent,
    NzFormControlComponent,
    NzFormDirective,
    NzFormItemComponent,
    NzInputDirective,
    NzInputGroupComponent,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;
  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private message:NzMessageService,
              private router: Router,) {}

  ngOnInit() {
    this.loginForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
    })
  }
  submitForm(): void {
    this.authService.login(this.loginForm.value).subscribe(res=>{
      console.log(res);
      if(res.userId!=null){
        const user={
          id:res.userId,
          role:res.userRole,
        }
        UserStorageService.saveUser(user)
        UserStorageService.saveToken(res.token)
        if(UserStorageService.isAdminLoggedIn()){
          this.router.navigateByUrl('/admin/dashboard');
        }else if (UserStorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl('/customer/rooms');
        }

      }
    },error=>{
      this.message.error("bad credentials",{nzDuration:5000});
      }
    )
  }

}
