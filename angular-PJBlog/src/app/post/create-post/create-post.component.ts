import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { PjblogModel } from 'src/app/shared/pjblog-model';
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

  constructor(private router: Router, private postService: PostService,
    private pjblogService: PjblogService) { 
    this.postPayload = {
      post_Name : '',
      description: '',
      subblogName:'',
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
  }

  createPost(){
    this.postPayload.description = this.createPostForm.get('description').value;
    this.postPayload.post_Name = this.createPostForm.get("postName").value;
    this.postPayload.subblogName = this.createPostForm.get('pjsubblogname').value;
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

}
