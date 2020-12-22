import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { CommentPayload } from 'src/app/comments/comment-payload';
import { CommentService } from 'src/app/comments/comment.service';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';
import { AuthService } from '../shared/auth.service';

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
  selectedFile: File;
  message: string;
  retrivedImage: any;
  retriveResponse: any;
  base64Data: any;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
    private commentService: CommentService, private authService: AuthService) { 
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

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  onUpload(){
    console.log(this.selectedFile);
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    this.authService.uploadImage(uploadImageData).subscribe(
      response => {
        this.message = "Upload successful";
      },
      error => {
        console.log(error);
        this.message = "Upload Error!";
      }
    );
  }

  getImage() {
    this.authService.getImage(this.selectedFile.name).subscribe(response => {
    this.retriveResponse = response;  
    this.base64Data = this.retriveResponse.picByte;
    this.retrivedImage = 'data:image/jpeg;base64,' + this.base64Data;
    });
  }

}
