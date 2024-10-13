import {Category} from "./category";
import {Photo} from "./photo";

export class ProductList {
  id!: number;
  name!: string;
  price!: number;
  mainImageUrl!: string;
}

export class Product {
  id!: number;
  name!: string;
  description!: string;
  price!: number;
  mainImageUrl!: string;
  category!: Category;
  photos!: Photo[];
}
