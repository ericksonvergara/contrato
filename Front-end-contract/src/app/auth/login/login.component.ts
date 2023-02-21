import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { LoginUser } from 'src/app/models/login-user';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  formLoginUser: FormGroup;
  isLogin = false;
  isLoginErr = false;
  loginUser: LoginUser = new LoginUser('', '');
  msgErr: string = '';
  roles: string[] = [];

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) {
    this.formLoginUser = this.createFormLoginUser();
  }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLogin = true;
      this.isLoginErr = false;
      this.roles = this.tokenService.getAuthorities();
    }
  }

  createFormLoginUser(): FormGroup {
    return new FormGroup({
      dni: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  login() {
    this.loginUser = this.formLoginUser.value;
    this.authService.login(this.loginUser).subscribe(
      (data) => {
        this.isLogin = true;
        this.isLoginErr = false;
        this.tokenService.setToken(data.token);
        this.tokenService.setUserName(data.userName);
        this.tokenService.setAuthorities(data.authorities);
        this.router.navigateByUrl('dasboard');
        console.log(data);
      },
      (error) => {
        this.isLogin = false;
        this.isLoginErr = true;
        this.msgErr = error.error.msg;
        console.log(this.msgErr);
      }
    );
  }

  get dni() {
    return this.formLoginUser.get('dni');
  }
  get password() {
    return this.formLoginUser.get('password');
  }
}
