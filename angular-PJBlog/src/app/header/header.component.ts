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
  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.userName = this.authService.getUsername();
    this.isLoggedIn = this.authService.isLoggedin();
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
}
