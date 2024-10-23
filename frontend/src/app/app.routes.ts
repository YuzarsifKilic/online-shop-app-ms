import { Routes } from '@angular/router';
import {ProductDetailComponent} from "./product/product-detail/product-detail.component";
import {ProductListComponent} from "./product/product-list/product-list.component";
import {LoginComponent} from "./login/login.component";
import {CartComponent} from "./cart/cart.component";
import {CustomerProfileComponent} from "./customer/customer-profile/customer-profile.component";

export const routes: Routes = [
  {
    path: '',
    component: ProductListComponent
  },
  {
    path: 'product-detail/:productId',
    component: ProductDetailComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'cart',
    component: CartComponent
  },
  {
    path: 'customer/profile',
    component: CustomerProfileComponent
  }
];
