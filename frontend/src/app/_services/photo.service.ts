import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {PRODUCT_SERVICE} from "../consts/product-service";
import {Photo} from "../_models/photo";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  constructor(private http: HttpClient) { }

  getPhotos(productId: number): Observable<Photo[]> {
    return this.http.get<Photo[]>(`${PRODUCT_SERVICE.PHOTO}/product/${productId}`);
  }
}
