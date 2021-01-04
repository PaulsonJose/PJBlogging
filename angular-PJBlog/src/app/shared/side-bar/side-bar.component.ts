import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ViewPjblogService } from '../pjblog-side-bar/view-pjblog.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  constructor(private router: Router, private viewPjblogService: ViewPjblogService) { }

  ngOnInit(): void {
  }

  goToCreatePost() {
    //this.viewPjblogService.setSubblogId = 0;
      this.router.navigateByUrl('/create-post');
  }

  goToCreateSubblog() {
    this.router.navigateByUrl('/create-pjblog');
  }
}
