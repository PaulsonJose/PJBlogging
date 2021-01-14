import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/shared/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  userName: string;
  isLoggedIn: boolean;

  retrivedImage: any;
  retriveResponse: any;
  base64Data: any;
  imageAvbl: boolean;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.userName = this.authService.getUsername();
    this.isLoggedIn = this.authService.isLoggedin();
    this.getImage();
    this.authService.events.forEach(event => {
      console.log("Event triggered: " + event);
      if(event === "updateHeaderImage") {
        this.getImage();
      }
    });
  }

  goToUserProfile() {
    this.router.navigateByUrl('/user-profile/' + this.userName);
  }

  logout() {
    this.authService.logout();
    this.router.navigateByUrl('/').then(()=> {
      window.location.reload();
    })
  }
  getImage() {
    this.authService.getImage(this.authService.getUsername()).subscribe(response => {
    this.retriveResponse = response;  
    this.base64Data = this.retriveResponse.picByte;
    this.retrivedImage = 'data:image/jpeg;base64,' + this.base64Data;
    this.imageAvbl = true
    console.log("Header Component: Image response received!");
    },
    error => {
      this.imageAvbl = false;
    });
  }
}
