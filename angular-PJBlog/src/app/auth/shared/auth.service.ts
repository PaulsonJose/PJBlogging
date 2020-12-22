import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from'@angular/common/http';
import { SignupRequestPayload } from '../sign-up/signup-request.payload';
import { EMPTY, Observable, throwError } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { LoginRequestPayload } from '../login/login-request.payload';
import { LoginResponsePayload } from '../login/login-response.payload';
import { catchError, map, share, tap } from 'rxjs/operators';
import { RefreshRequestPayload } from '../login/refresh-request.payload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {   }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post('http://localhost:8081/api/auth/signup',signupRequestPayload, {responseType: 'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<Boolean>{
    return this.http.post<LoginResponsePayload>('http://localhost:8081/api/auth/login',loginRequestPayload)
    .pipe( map(data => {
        this.localStorage.store("authenticationTocken", data.tocken);
        this.localStorage.store("expiresAt", data.expiresAt);
        this.localStorage.store("refreshTocken", data.refreshTocken);
        this.localStorage.store("userName", data.username);
        return true;
      }));
  }

  getJWTTocken(){
    //console.log("Tocken: " + this.localStorage.retrieve('authenticationTocken'));
    return this.localStorage.retrieve('authenticationTocken');
  }

  isTockenExpired(): Boolean{
    let tockenExp: Date;
    let dateNow: Date;
    dateNow = new Date(Date.now());
    tockenExp= new Date(this.localStorage.retrieve('expiresAt'));
    /*console.log(dateNow.getTime());
    console.log(tockenExp.getTime());
    console.log(dateNow>=tockenExp);*/
    return (dateNow.getTime()>=tockenExp.getTime());
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
      `body was: ${error.error}`);
        console.log(error);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }
  

  refreshTocken(){
    let refreshTockenPayload: RefreshRequestPayload;
    refreshTockenPayload = {
      refreshTocken: this.getRefreshTocken(),
      userName: this.getUsername()
    }
    console.log(refreshTockenPayload);
    return this.http.post<LoginResponsePayload>('http://localhost:8081/api/auth/refresh/tocken', refreshTockenPayload)
    .pipe(
      share(), // <========== YOU HAVE TO SHARE THIS OBSERVABLE TO AVOID MULTIPLE REQUEST BEING SENT SIMULTANEOUSLY
      map(res => {
        console.log(res);
        const token = res.tocken;
        const newRefreshToken = res.refreshTocken;
        // store the new tokens
        this.localStorage.store('refreshTocken', newRefreshToken);
        this.localStorage.store('authenticationTocken', token);
        this.localStorage.store('expiresAt', res.expiresAt);
        this.localStorage.store('username', res.username);
        return token;
      }),
      catchError((err) => {return this.handleError(err);})
    );
  }

  isLoggedin() {

    console.log(this.localStorage.retrieve('username'));
    return (this.localStorage.retrieve('username') != null? true: false);
  }

  getRefreshTocken(){
    return this.localStorage.retrieve('refreshTocken');
  }

  getUsername(){
    return this.localStorage.retrieve('username');
  }

  logout(){
    let refreshTockenPayload: RefreshRequestPayload;
    refreshTockenPayload = {
      refreshTocken: this.getRefreshTocken(),
      userName: this.getUsername()
    }
    this.http.post('http://localhost:8081/api/auth/logout', refreshTockenPayload, {responseType: 'text'}).subscribe( data => {
      console.log(data);
    }, error => {
      throwError(error);
    });
    this.localStorage.clear('refreshTocken');
    this.localStorage.clear('authenticationTocken');
    this.localStorage.clear('expiresAt');
    this.localStorage.clear('username');
  }

  uploadImage(uploadImageData: FormData):Observable<HttpResponse<any>>{
    return this.http.post<HttpResponse<any>>('http://localhost:8081/image/upload', uploadImageData, {observe: 'response'});
  }

  getImage(fileName: string): Observable<any> {
      return this.http.get<any>('http://localhost:8081/image/get/' + fileName);

  }
}
