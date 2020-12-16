import { Component, OnInit } from '@angular/core';
import { PjblogModel } from '../pjblog-model';
import { PjblogService } from '../pjblog.service';

@Component({
  selector: 'app-pjblog-side-bar',
  templateUrl: './pjblog-side-bar.component.html',
  styleUrls: ['./pjblog-side-bar.component.css']
})
export class PjblogSideBarComponent implements OnInit {

  displayAllView: boolean = true;
  subblogs: Array<PjblogModel> = [];
  constructor(private pjblogService: PjblogService) { 
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

}
