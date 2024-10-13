import { Injectable } from '@angular/core';
import {Filter} from "../_models/filter";
import {HttpClient, HttpParams} from "@angular/common/http";
import {PRODUCT_SERVICE} from "../consts/product-service";
import {Product, ProductList} from "../_models/product";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  filters: Array<keyof Filter> = ["name", "min", "max", "categoryId"];

  getProducts(filter: Filter): Observable<ProductList[]> {
    let params = new HttpParams();

    for (let key of this.filters) {
      if (filter[key] !== undefined && filter[key] !== null) {
        params = params.append(key, filter[key].toString());
      }
    }
    return this.http.get<ProductList[]>(`${PRODUCT_SERVICE.PRODUCT}`, { params });
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${PRODUCT_SERVICE.PRODUCT}/${id}`);
  }
}
