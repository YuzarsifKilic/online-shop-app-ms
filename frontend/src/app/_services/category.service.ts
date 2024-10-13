import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Category} from "../_models/category";
import {Observable} from "rxjs";
import {PRODUCT_SERVICE} from "../consts/product-service";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(PRODUCT_SERVICE.CATEGORY);
  }
}
