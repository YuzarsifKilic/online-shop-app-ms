import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CreateOrderRequest} from "../_models/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  createOrder(createOrderRequest: CreateOrderRequest) {
    return this.http.post('http://localhost:8086/api/v1/orders', createOrderRequest);
  }
}
