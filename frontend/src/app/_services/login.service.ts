import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthResponse} from "../_models/auth-response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  login(email: string, password: string) {
    return this.http.post<AuthResponse>('http://localhost:8086/api/v1/customers/login', {email, password});
  }
}
