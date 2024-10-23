import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ValidateCustomer} from "../_models/customer";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient, private userService: UserService) { }

  public validateCustomer() {
    return this.http.get<ValidateCustomer>("http://localhost:8086/api/v1/customers/validate")
      .subscribe(
        (customer) => {
          console.log(customer);
          this.userService.setName(customer.firstName + " " + customer.lastName);
          this.userService.setUserId(customer.id);
        }
      )
  }


}
