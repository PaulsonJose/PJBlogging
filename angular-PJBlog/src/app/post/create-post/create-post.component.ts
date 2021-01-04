import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { PjblogModel } from 'src/app/shared/pjblog-model';
import { ViewPjblogService } from 'src/app/shared/pjblog-side-bar/view-pjblog.service';
import { PjblogService } from 'src/app/shared/pjblog.service';
import { PostService } from 'src/app/shared/post.service';
import { CreatePostPayload } from './create-post-payload';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: CreatePostPayload;
  pjblogs: Array<PjblogModel>;
  pjblog: PjblogModel;
  blogFound: boolean = false;
  selectedSubblogId: number;

  constructor(private router: Router, private postService: PostService,
    private pjblogService: PjblogService, private viewPjblogService: ViewPjblogService) { 
    this.postPayload = {
      post_Name : '',
      description: '',
      subblogId: 0,
      url:''
    };
  }

  ngOnInit(): void {

    this.createPostForm = new FormGroup({
      postName : new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      url: new FormControl('',Validators.required),
      pjsubblogname: new FormControl('', Validators.required)
    });
    this.pjblogService.getAllSubblogs().subscribe(data => {
      this.pjblogs = data;
      },
      error => {
      throwError(error);
    });
    this.selectedSubblogId = this.viewPjblogService.getSubblogId;
    if (this.selectedSubblogId === undefined) {
      this.selectedSubblogId = 0;
    } else {
      this.getSubblogDetails(this.selectedSubblogId.toString());
    }
    console.log("selected Id: " + this.selectedSubblogId);
  }

  createPost(){
    this.postPayload.description = this.createPostForm.get('description').value;
    this.postPayload.post_Name = this.createPostForm.get("postName").value;
    this.postPayload.subblogId = this.createPostForm.get('pjsubblogname').value;
    this.postPayload.url = this.createPostForm.get('url').value;

    this.postService.createPost(this.postPayload).subscribe(data => {
        this.router.navigateByUrl('/');
    },
    error => {
      throwError(error);
    });
  }
  discardPost() {
    this.router.navigateByUrl('/');
  }

  getSubblogDetails(subblogName: String){
    //console.log(subblogName);
    this.pjblogService.getPJBlog(subblogName).subscribe(data => {
      this.pjblog = data;
      this.blogFound = true;
    },
    error => {
      this.blogFound = false;
    });
  }

}
