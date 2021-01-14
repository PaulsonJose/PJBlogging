import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create-post-payload';
import { PostModel } from './post-model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  getPost(postId: number):Observable<PostModel>{
    return this.http.get<PostModel>('http://localhost:8081/api/posts/' + postId);
  }

  constructor(private http: HttpClient) { }

  getAllPosts():Observable<Array<PostModel>> {
      return this.http.get<Array<PostModel>>('http://localhost:8081/api/posts');
  }

  createPost(createPostPayload: CreatePostPayload):Observable<HttpResponse<any>>{
    return this.http.post<HttpResponse<any>>('http://localhost:8081/api/posts', createPostPayload);
  }
  getAllPostsByUser(username: string): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>('http://localhost:8081/api/posts/by-user/' + username);
  }
}
