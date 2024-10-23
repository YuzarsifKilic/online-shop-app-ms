export class Order {

}

export class CreateOrderRequest {
  userId!: string;
  products!: ProductRequest[];
}

export class ProductRequest {
  productId!: number;
  quantity!: number;
}
