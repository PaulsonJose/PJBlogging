import { Component, Input, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {faComments} from '@fortawesome/free-solid-svg-icons';
import { CommentService } from 'src/app/comments/comment.service';
import { PostModel } from '../post-model';
import { PostService } from '../post.service';
import { UserDisplayModelComponent } from '../user-display-model/user-display-model.component';
import { UserDisplayModelConfig } from '../user-display-model/user-display-model.config';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

  //@Input() data: Array<PostModel>;
modalConfig: UserDisplayModelConfig;

  @ViewChild('modal', { static: false }) private modalComponent: UserDisplayModelComponent;


  faComments = faComments;
  @Input() posts: PostModel[];
  postUser: string;

  constructor(private router: Router, private postService: PostService, private commentService: CommentService) { }

  ngOnInit(): void {
    this.modalConfig = {
      modalTitle: "User Details",
      disableCloseButton: (()=> {
        return false;
      }),
      hideDismissButton: (()=> {
        return true;
      }),
      //dismissButtonLabel: "Dismiss",
      closeButtonLabel: "Close"
    };
  }

  async openModal(userName: string) {
    try{
      this.modalConfig.modalTitle = "Details of " + userName;
      this.postService.getAllPostsByUser(userName).subscribe(data => {
        this.modalConfig.postLen = data.length;
      });
      this.commentService.getAllCommentsByUser(userName).subscribe(data => {
        this.modalConfig.commentLng = data.length;
      });
      console.log(this.modalComponent);
    return await this.modalComponent.open();
    } catch(e) {
      console.log(e);
    }
  
  }

  goToPost(id: number): void{
      this.router.navigateByUrl('/view-post/' + id);
  }
}
