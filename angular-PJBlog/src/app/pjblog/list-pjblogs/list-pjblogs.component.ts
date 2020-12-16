import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { PjblogModel } from 'src/app/shared/pjblog-model';
import { PjblogService } from 'src/app/shared/pjblog.service';

@Component({
  selector: 'app-list-pjblogs',
  templateUrl: './list-pjblogs.component.html',
  styleUrls: ['./list-pjblogs.component.css']
})
export class ListPjblogsComponent implements OnInit {

  pjblogs: Array<PjblogModel>;
  constructor(private pjblogService: PjblogService) { }

  ngOnInit(): void {
    this.pjblogService.getAllSubblogs().subscribe(data => {
      this.pjblogs = data;
    },
    error => {
      throwError (error);
    });
  }

}
