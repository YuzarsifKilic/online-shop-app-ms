import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LoginService} from "../_services/login.service";
import {CustomerService} from "../_services/customer.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private formBuilder: FormBuilder, private loginService: LoginService, private customerService: CustomerService) { }

  form = new FormGroup({
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", Validators.required)
  })

  login() {
    this.loginService.login(this.form.value.email!, this.form.value.password!).subscribe(
      (response) => {
        window.localStorage.setItem("token", response.token);
        window.localStorage.setItem("role", response.role);
        window.localStorage.setItem("userId", response.userId);
        this.customerService.validateCustomer();
      }
    );
  }
}
