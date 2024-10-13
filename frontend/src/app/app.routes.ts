import { Routes } from '@angular/router';
import {ProductDetailComponent} from "./product/product-detail/product-detail.component";
import {ProductListComponent} from "./product/product-list/product-list.component";

export const routes: Routes = [
  {
    path: '',
    component: ProductListComponent
  },
  {
    path: 'product-detail/:productId',
    component: ProductDetailComponent
  }
];
