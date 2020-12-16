import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { throwError } from 'rxjs';
import { CommentPayload } from 'src/app/comments/comment-payload';
import { CommentService } from 'src/app/comments/comment.service';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  postId: number;
  post: PostModel;
  commentForm: FormGroup;
  commentPayload: CommentPayload;
  comments: CommentPayload[];

  constructor(private activatedRouter: ActivatedRoute, private postService: PostService,
    private commentService: CommentService, private router: Router) { 
    this.postId = this.activatedRouter.snapshot.params.id;

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });
    this.commentPayload = {
      text : '',
      postId: this.postId
    };
    this.post = {
      commentCount : 0,
      description : '',
      duration: '',
      postCount: 0,
      postName: '',
      subblogName:'',
      id : 0,
      url: '',
      userName: '',
      voteCount: 0,
      downVote:undefined,
      upVote:undefined
    };
  }

  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
  }

  postComment() {
    this.commentPayload.text = this.commentForm.get('text').value;
    //this.commentPayload.postId = this.postId;
    this.commentService.postComment(this.commentPayload).subscribe(data => {
      this.commentForm.get('text').setValue('');
      this.getCommentsForPost();
    },
    error =>{
      throwError(error);
    })
  }

  getCommentsForPost(){
    this.commentService.getAllCommentsForPost(this.postId).subscribe(data => {
      this.comments = data;
    },
    error=> {
      throwError(error);
    })
  }

  getPostById() {
    this.postService.getPost(this.postId).subscribe(data => {
      this.post = data;
    },
    error => {
      throwError(error);
    });
  }
}
