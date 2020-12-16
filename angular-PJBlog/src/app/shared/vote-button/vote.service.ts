import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable, throwError } from 'rxjs';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { VotePayload } from './vote-payload';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http: HttpClient, private authService: AuthService, private toastr: ToastrService) { }

  postVote(votePayload: VotePayload): Observable<any> {
    if(!this.authService.isLoggedin()) {
      this.toastr.error("Please login to vote.");
      return throwError("Login Required");
    }
    return this.http.post('http://localhost:8081/api/votes',votePayload,{responseType: 'text'});
  }

}
