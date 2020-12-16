import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { SignupRequestPayload } from './signup-request.payload';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signupForm: FormGroup;
  signupRequestPayload: SignupRequestPayload;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) { 
     this.signupRequestPayload={
       user: '',
       email: '',
       password: '',
       userRole:''
     }
  }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  signup(){
    this.signupRequestPayload.user = this.signupForm.get('username').value;
    this.signupRequestPayload.email = this.signupForm.get('email').value;
    this.signupRequestPayload.password = this.signupForm.get('password').value;
    this.signupRequestPayload.userRole = 'ROLE_USER';
    this.authService.signup(this.signupRequestPayload).subscribe(()=>{
      console.log ("User Registration Successful");
      this.router.navigate(['/login'],{queryParams: {registered: 'true'}});
    },
    ()=>{
      console.log("User Registration failed");
      this.toastr.error("Signup failed! Please try again");
    });
  }
}