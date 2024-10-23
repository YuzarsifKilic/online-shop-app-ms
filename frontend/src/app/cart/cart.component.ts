import {ChangeDetectorRef, Component} from '@angular/core';
import {UserService} from "../_services/user.service";
import {CartService} from "../_services/cart.service";
import {Cart, Product} from "../_models/cart";
import {NgForOf, NgIf} from "@angular/common";
import {CustomerService} from "../_services/customer.service";
import Swal from "sweetalert2";
import {OrderService} from "../_services/order.service";
import {CreateOrderRequest, ProductRequest} from "../_models/order";
declare var bootstrap: any;

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {

  constructor(private userService: UserService,
              private cartService: CartService,
              private customerService: CustomerService,
              private cd: ChangeDetectorRef,
              private orderService: OrderService) { }

  userId!: string;
  cart!: Cart;
  showDeleteAlert: boolean = false;
  lastUndoProduct!: Product;

  ngAfterViewInit() {
    const toastElList = document.querySelectorAll('.toast');
    toastElList.forEach((toastEl) => {
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    });
  }

  ngOnInit(): void {
    this.customerService.validateCustomer();
    this.userService.$userId.subscribe(userId => {
      this.userId = userId
      this.cartService.getCartByCustomerId(this.userId)
        .subscribe(
          (cart) => {
            this.cart = cart
          }
        )
    });

  }

  decreaseQuantity(product: Product) {
    this.cartService.createCart(this.userId, product.productId, -1)
      .subscribe(
        (response) => {
          this.cart = response;
        }
      )
  }

  increaseQuantity(product: Product) {
    this.cartService.createCart(this.userId, product.productId, 1)
      .subscribe(
        (response) => {
          this.cart = response;
        }
      )
  }

  deleteProduct(product: Product) {
    this.cartService.createCart(this.userId, product.productId, -1)
      .subscribe(
        (response) => {
          this.cart = response;
          this.showDeleteAlert = true;
          this.lastUndoProduct = product;
          this.cd.detectChanges();
          this.showToast();
        }
      )
  }

  undoProduct() {
    this.cartService.createCart(this.userId, this.lastUndoProduct.productId, 1)
      .subscribe(
        (response) => {
          this.cart = response;
          this.showDeleteAlert = false;
        }
      )
  }

  showToast() {
    const toastElList = document.querySelectorAll('.toast');
    toastElList.forEach((toastEl) => {
      const toast = new bootstrap.Toast(toastEl);
      toast.show();
    });
  }

  order() {
    let products:  ProductRequest[] = [];
    this.cart.products.forEach((product: Product) => {
      let productRequest = new ProductRequest();
      productRequest.productId = product.productId;
      productRequest.quantity = product.quantity;
      products.push(productRequest);
    })
    let createOrderRequest = new CreateOrderRequest();
    createOrderRequest.userId = this.userId;
    createOrderRequest.products = products;
    this.orderService.createOrder(createOrderRequest)
      .subscribe(
        (response) => {
          Swal.fire({
            title: "The Internet?",
            text: "That thing is still around?",
            icon: "success",
            timer: 2000,
          });
        }
      );
  }
}
