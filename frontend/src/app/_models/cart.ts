export class Cart {
  customerId!: string;
  products!: Product[];
}

export class Product {
  productId!: number;
  name!: string;
  price!: number;
  mainImageUrl!: string;
  quantity!: number;
  stock!: number;
}
