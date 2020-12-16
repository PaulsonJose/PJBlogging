import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PjblogModel } from 'src/app/shared/pjblog-model';
import { PjblogService } from 'src/app/shared/pjblog.service';

@Component({
  selector: 'app-create-pjblog',
  templateUrl: './create-pjblog.component.html',
  styleUrls: ['./create-pjblog.component.css']
})
export class CreatePjblogComponent implements OnInit {

  createPjBlogForm:FormGroup;
  pjBlogModel: PjblogModel;
  title= new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private pjblogService: PjblogService) { 
    this.createPjBlogForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('',Validators.required)
    });
    this.pjBlogModel = {
      name : '',
      description: '',
      id:0,
      postCount:0
    }
  }

  ngOnInit(): void {
  }
  discard(){
      this.router.navigateByUrl('/');
  }

  createBlog(){
    this.pjBlogModel.name = this.createPjBlogForm.get('title').value;
    this.pjBlogModel.description=this.createPjBlogForm.get('description').value;
    this.pjBlogModel.postCount = 0;
    console.log("Sending post Request.");
    this.pjblogService.createPJBlog(this.pjBlogModel).subscribe(data =>{
      this.router.navigateByUrl('/list-pjblogs');
    },
    error => {
      console.log("Error Occured: ");
      console.log(error);
    });
  }
}
