import { Component } from '@angular/core';
import {FilterComponent} from "../../filter/filter.component";
import {HttpClientModule} from "@angular/common/http";
import {Filter} from "../../_models/filter";
import {ProductService} from "../../_services/product.service";
import {debounceTime} from "rxjs";
import {ProductList} from "../../_models/product";
import {ProductCardComponent} from "../product-card/product-card.component";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    FilterComponent,
    HttpClientModule,
    ProductCardComponent,
    NgForOf,
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getProducts(this.filterModel)
      .subscribe({
        next: (response) => {
          this.products = response;
        },
        error: (error) => {
          console.error('Error fetching data', error);
        }
      });
  }

  products: ProductList[] = [];
  filterModel: Filter = new Filter();

  filter(event: Filter) {
    this.productService.getProducts(event)
      .subscribe({
        next: (response) => {
          this.products = response;
        },
        error: (error) => {
          console.error('Error fetching data', error);
        }
      });
  }
}
