import { Component, Input, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserDisplayModelConfig } from './user-display-model.config';

@Component({
  selector: 'app-user-display-model',
  templateUrl: './user-display-model.component.html',
  styleUrls: ['./user-display-model.component.css']
})
export class UserDisplayModelComponent implements OnInit {

  @Input() public userModelConfig: UserDisplayModelConfig;
  @ViewChild('app-user-display-model') private modalContant: TemplateRef<UserDisplayModelComponent>; 
  private modelRef: NgbModalRef;

  constructor(private modelService: NgbModal) { }

  ngOnInit(): void {
  }

  open() : Promise<boolean> {
    console.log("open method start");
    return new Promise<boolean>(resolve => {
    this.modelRef = this.modelService.open(this.modalContant);
    console.log("open method done");
    this.modelRef.result.then();
  });
  }

  async close(): Promise<void> {
    if(this.userModelConfig.shouldClose === undefined || (await this.userModelConfig.shouldClose())) {
      const result = this.userModelConfig.onClose === undefined || (await this.userModelConfig.onClose());
      this.modelRef.close(result);
    }
  }

  async dismiss(): Promise<void> {
    if(this.userModelConfig.shouldDismiss === undefined || (await this.userModelConfig.shouldDismiss())) {
      const result = this.userModelConfig.onDismiss === undefined || (await this.userModelConfig.onDismiss());
      this.modelRef.dismiss(result);
    }
  }

}
