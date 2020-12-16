import { Component, Input, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {faComments} from '@fortawesome/free-solid-svg-icons';
import { PostModel } from '../post-model';
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

  @ViewChild('modal') private modalComponent: UserDisplayModelComponent;


  faComments = faComments;
  @Input() posts: PostModel[];

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.modalConfig = {
      modalTitle: "User Details"
    };
  }

  async openModal() {
    try{
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
