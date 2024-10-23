import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Cart} from "../_models/cart";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  getCartByCustomerId(customerId: string) {
    return this.http.get<Cart>("http://localhost:8086/api/v1/carts/customer/" + customerId);
  }

  createCart(customerId: string, productId: number, quantity: number) {
    return this.http.post<Cart>("http://localhost:8086/api/v1/carts", {customerId, productId, quantity});
  }
}
