import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PjblogModel } from './pjblog-model';

@Injectable({
  providedIn: 'root'
})
export class PjblogService {

  constructor(private http: HttpClient) { }

  getAllSubblogs(): Observable<Array<PjblogModel>> {
    return this.http.get<Array<PjblogModel>>('http://localhost:8081/api/pjsubblog');
  }

  createPJBlog(pjBlogModel: PjblogModel): Observable<PjblogModel>{
      return this.http.post<PjblogModel>('http://localhost:8081/api/pjsubblog', pjBlogModel);
  }

  getPJBlog(pjblogId: String): Observable<PjblogModel> {
    return this.http.get<PjblogModel>('http://localhost:8081/api/pjsubblog/' + pjblogId);
  }

}
