import {Component, Input} from '@angular/core';
import {ProductList} from "../../_models/product";
import {Router} from "@angular/router";

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css'
})
export class ProductCardComponent {

  @Input() product!: ProductList;

  constructor(private router: Router) { }

  productDetail(id: number) {
    this.router.navigate(['/product-detail', id]);
  }
}
