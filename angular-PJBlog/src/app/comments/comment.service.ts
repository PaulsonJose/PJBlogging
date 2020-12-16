import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentPayload } from './comment-payload';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  getAllCommentsForPost(postId: number): Observable<Array<CommentPayload>> {
    return this.http.get<Array<CommentPayload>>('http://localhost:8081/api/comments/' + postId);
  }

  postComment(commentPayload: CommentPayload): Observable<HttpResponse<any>> {
    return this.http.post<HttpResponse<any>>('http://localhost:8081/api/comments',commentPayload);
  }
  getAllCommentsByUser(username: string): Observable<Array<CommentPayload>> {
    return this.http.get<Array<CommentPayload>>('http://localhost:8081/api/comments/user/' + username);
  }

}
