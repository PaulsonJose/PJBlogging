import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ViewPjblogService {

  constructor() { }

  private subblogId: number;

  set setSubblogId(newSubblogId: number) {
      this.subblogId = newSubblogId;
  }

  get getSubblogId(): number {
    return this.subblogId;
  }
}
