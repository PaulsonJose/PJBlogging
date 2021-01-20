import { JitCompiler } from '@angular/compiler';
import { Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { WebcamImage, WebcamInitError, WebcamUtil } from 'ngx-webcam';
import { Observable, Subject, throwError } from 'rxjs';
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
  imageAvbl: boolean;
  closeResult = '';


  // webcam snapshot trigger
    private trigger: Subject<void> = new Subject<void>();

    public webcamImage: WebcamImage = null;
    deviceId: string;
    // switch to next / previous / specific webcam; true/false: forward/backwards, string: deviceId
    private nextWebcam : Subject<boolean|string> = new Subject<boolean|string>();
    public multipleWebcamsAvailable:boolean = false;
    mediaDeviceInfo: MediaDeviceInfo[];
    mediaDeviceIndex: number = 0;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
    private commentService: CommentService, private authService: AuthService, private cameraService: NgbModal) { 
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
    this.getImage();
    WebcamUtil.getAvailableVideoInputs().then((mediaDeviceInfo: MediaDeviceInfo[]) => {
      this.multipleWebcamsAvailable = mediaDeviceInfo && mediaDeviceInfo.length > 1;
      this.mediaDeviceInfo = mediaDeviceInfo;
      mediaDeviceInfo.forEach(function(device){
        console.log(device);
      });
    });
  }

  public onFileChanged(files) {
    if(files.length === 0) {
      return;
    }
    this.selectedFile = files[0];

    var reader = new FileReader();
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.retrivedImage = reader.result; 
    }

    //var output = <HTMLImageElement>document.getElementById('preview');
    //output.src = URL.createObjectURL(this.selectedFile);
    ///output.onload = function() {
    //  URL.revokeObjectURL(output.src) // free memory
    //}

  }

  onUpload(){
    console.log("uploading: " + this.selectedFile.name);
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
    this.authService.uploadImage(uploadImageData).subscribe(
      response => {
        console.log(response);
        this.message = "Upload successful";
        this.getImage();
      },
      error => {
        console.log(error);
        this.message = "Upload Error!";
      }
    );
  }

  startCamWindow(content) {
    console.log("Starting Camera");
    this.cameraService.open(content, 
      {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => { 
         this.closeResult = result; 
       }, (reason) => { 
         this.closeResult =  
            "Dismissed " + this.getDismissReason(reason); 
       });
  }

  preview_bild(){
    const b64toBlob = (b64Data, contentType='', sliceSize=512) => {
      const byteCharacters = atob(b64Data);
      const byteArrays = [];
    
      for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        const slice = byteCharacters.slice(offset, offset + sliceSize);
    
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
    
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }
    
      const blob = new Blob(byteArrays, {type: contentType});
      return blob;
    }
    const contentType = 'image/jpeg';
    this.retrivedImage =this.webcamImage.imageAsDataUrl;
    
    (<HTMLInputElement>document.getElementById("fileSelector")).value = "";
    const blob = b64toBlob(this.webcamImage.imageAsBase64, contentType);
    this.selectedFile = new File([blob], "userPic.jpg",{type: 'image/jpeg', lastModified: Date.now()});
  }

  private getDismissReason(reason: any): string { 
    if (reason === ModalDismissReasons.ESC) { 
      return 'by pressing ESC'; 
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) { 
      return 'by clicking on a backdrop'; 
    } else { 
      return 'with: '+ reason; 
    } 
  } 

  public handleInitError(error: WebcamInitError): void {
    if (error.mediaStreamError && error.mediaStreamError.name === "NotAllowedError") {
      console.warn("Camera access was not allowed by user!");
    }
  }
  
  public triggerSnapshot() : void{
    this.trigger.next();
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  public handleImage(webcamImage: WebcamImage): void {
    console.info("received webcam Image" + webcamImage);
    this.webcamImage = webcamImage;
  }

  public cameraWasSwitched(deviceId: string): void{
    console.log("Active device: " + deviceId);
    this.deviceId = deviceId;
  }

  public get nextWebcamObservable() : Observable<boolean|string> {
    return this.nextWebcam.asObservable();
  }

  public showNextWebcam() :void {
    // true => move forward through devices
    // false => move backwards through devices
    // string => move to device with given deviceId
    try {
      if(this.mediaDeviceIndex >= this.mediaDeviceInfo.length - 1) {
        this.mediaDeviceIndex = 0;
      } else {
        this.mediaDeviceIndex += 1;
      }
      //console.log("Index: "+ this.mediaDeviceIndex + "Device Id: " + this.mediaDeviceInfo[this.mediaDeviceIndex].deviceId);
      this.nextWebcam.next(this.mediaDeviceInfo[this.mediaDeviceIndex].deviceId);
    }
    catch(e){
      this.mediaDeviceIndex += 1;
      console.log("Error: " + e);
    };
  }

  getImage() {
    this.authService.getImage(this.authService.getUsername()).subscribe(response => {
    this.retriveResponse = response;  
    this.base64Data = this.retriveResponse.picByte;
    this.retrivedImage = 'data:image/jpeg;base64,' + this.base64Data;
    console.log("response received!");
    this.authService.newEvent('updateHeaderImage');
    },
    error => {
      console.log("Image not available!");
    });
  }

}
