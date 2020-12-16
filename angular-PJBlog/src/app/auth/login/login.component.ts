import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { LoginRequestPayload } from './login-request.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  isError: Boolean;
  registerSuccessMessage: string;

  loginRequestPayload: LoginRequestPayload;
  constructor(private authService: AuthService, private toastr: ToastrService, 
    private activatedRoute: ActivatedRoute, private router: Router) { 
      this.loginRequestPayload = {
        username : "",
        password : ""
      };
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('',Validators.required),
      password: new FormControl('', Validators.required)
    });
    this.activatedRoute.queryParams.subscribe(params => {
      if(params.registered!== undefined && params.registered === 'true') {
        console.log("Signup successful");
        this.toastr.success("Signup successful");
        this.registerSuccessMessage = 'Please check your inbox for activation mail.' +
        'Activate your account before logging in';
      }
    });
  }

  login(){
    this.loginRequestPayload.username = this.loginForm.get("username").value;
    this.loginRequestPayload.password = this.loginForm.get("password").value;
    this.authService.login(this.loginRequestPayload).subscribe( result =>{
      if (result) {        
        this.isError = false;
        this.router.navigateByUrl('/');
        console.log("Login successful");
        this.toastr.success("Login Success");
      }      
    },
    err => {
      this.isError = true;
        console.log("Login failed: " + err);
        this.toastr.error("Login Failed","PJ Blogging");
    });
  }
}
