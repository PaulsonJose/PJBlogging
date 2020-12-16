import { Component, Input, OnInit } from '@angular/core';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { ToastrService } from 'ngx-toastr';
import { PostModel } from '../post-model';
import { PostService } from '../post.service';
import { VoteModel } from '../vote-model';
import { VotePayload } from './vote-payload';
import { VoteType } from './vote-type';
import { VoteService } from './vote.service';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  @Input() post: PostModel;
  votePayload: VotePayload = {
    postId: 0,
    voteType:undefined
  };

  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor: string = "";
  downvoteColor: string = "";
  constructor(private postService: PostService, private voteService: VoteService, private toaster: ToastrService) { }

  ngOnInit(): void {
    this.updatePostDetails();
  }

  upvotePost(){
      this.votePayload.postId = this.post.id;
      this.votePayload.voteType = VoteType.UPVOTE;
      this.voteService.postVote(this.votePayload).subscribe(data => {
        //console.log(data);
        this.updatePostDetails();
        this.toaster.info(data);
      });
      console.log("Upvote Send");
  }
  downvotePost() {
      this.votePayload.postId = this.post.id;
      this.votePayload.voteType = VoteType.DOWNVOTE;
      this.voteService.postVote(this.votePayload).subscribe(data => {
        //console.log(data);
        this.updatePostDetails();
        this.toaster.info(data);
      });
      console.log("Downvote Send");
  }

  updatePostDetails() {
    this.postService.getPost(this.post.id).subscribe(data => {
      this.post = data;
    })
  }

}