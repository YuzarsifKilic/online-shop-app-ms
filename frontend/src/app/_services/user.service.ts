import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

  private name = new BehaviorSubject<string>("");
  $name = this.name.asObservable();
  private userId = new BehaviorSubject<string>("");
  $userId = this.userId.asObservable();

  setName(name: string) {
    this.name.next(name);
  }

  setUserId(userId: string) {
    this.userId.next(userId);
  }
}
