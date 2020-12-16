import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { AuthService } from './auth/shared/auth.service';
import { catchError, switchMap } from 'rxjs/operators';
import { LoginResponsePayload } from './auth/login/login-response.payload';

@Injectable()
export class TockenInterceptor implements HttpInterceptor {

  isTockenRefreshing = false;
  refreshTockenSubject = new BehaviorSubject(null);
  constructor(public authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.url.indexOf("refresh") > 0 || request.url.indexOf("login") > 0
    || (request.url.indexOf("/api/posts") > 0 && request.method.indexOf("GET") > 0)
    || (request.url.indexOf("/api/pjsubblog") > 0 && request.method.indexOf("GET") > 0)) {
      return next.handle(request);
    }
    if(this.authService.getJWTTocken()){
      if(this.authService.isTockenExpired() === true){
        console.log("Error Handle for refresh tocken");
        return this.handleAuthErrors(request, next);
      }
      console.log("Request Send: ");
      let httpRequest: HttpRequest<any>;
      httpRequest = this.addTocken(request, this.authService.getJWTTocken());
      console.log(httpRequest);
      return next.handle(httpRequest);
    }
    console.log("No Existing JWT found.");
    return next.handle(request);
    //return throwError("Not allowed");
  }
  private addTocken(req: HttpRequest<any>, JWTTocken: string) {
    return req.clone({
        headers: req.headers.set('Authorization','Bearer ' + JWTTocken)        
    });
}

private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler) {
    if(!this.isTockenRefreshing) {
        this.isTockenRefreshing = true;
        this.refreshTockenSubject.next(null);
        return this.authService.refreshTocken().pipe(
            switchMap((refreshTockenResponse: string) => {
                this.isTockenRefreshing = false;
                this.refreshTockenSubject.next(refreshTockenResponse);
                return next.handle(this.addTocken(req, refreshTockenResponse));
            })
        );
    } else {
      return next.handle(req);
    }
}
}