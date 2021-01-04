import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PjblogModel } from '../pjblog-model';
import { PjblogService } from '../pjblog.service';
import { ViewPjblogService } from './view-pjblog.service';

@Component({
  selector: 'app-pjblog-side-bar',
  templateUrl: './pjblog-side-bar.component.html',
  styleUrls: ['./pjblog-side-bar.component.css']
})
export class PjblogSideBarComponent implements OnInit {

  displayAllView: boolean = true;
  subblogs: Array<PjblogModel> = [];
  constructor(private pjblogService: PjblogService, private viewPjblogService: ViewPjblogService, private router: Router) { 
    this.pjblogService.getAllSubblogs().subscribe(subblog => {
      if(subblog.length>=4) {
        this.subblogs=subblog.splice(0,3);
        this.displayAllView=true;
      }else {
      this.subblogs = subblog;
      }
    })
  }

  ngOnInit(): void {
  }

  setSubblogId(subblogId: number){
    this.viewPjblogService.setSubblogId = subblogId;
    console.log("SubblogId set: " + subblogId);
    this.router.navigateByUrl('/create-post');
  }

}
