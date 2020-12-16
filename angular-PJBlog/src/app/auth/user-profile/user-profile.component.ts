import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { CommentPayload } from 'src/app/comments/comment-payload';
import { CommentService } from 'src/app/comments/comment.service';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  name: string;
  postLength: number;
  commentLength: number;
  posts: PostModel[];
  comments: CommentPayload[];
  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
    private commentService: CommentService) { 
      this.name = this.activatedRoute.snapshot.params.name;

      this.postService.getAllPostsByUser(this.name).subscribe(data => {
        this.posts = data;
        this.postLength = data.length;
      },
      error=> {
        throwError(error);
      });

      this.commentService.getAllCommentsByUser(this.name).subscribe(data=>{
        this.comments = data;
        this.commentLength = data.length;
      },
      error => {
        throwError(error);
      });
    }

  ngOnInit(): void {
  }

}
