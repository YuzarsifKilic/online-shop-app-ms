import {Category} from "./category";
import {Photo} from "./photo";
import {Company} from "./company";

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
  company!: Company;
}
