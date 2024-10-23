import { Component } from '@angular/core';
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import Swal from "sweetalert2";
import {Router} from "@angular/router";
import {UserService} from "../_services/user.service";

@Component({
  selector: 'app-header',
  standalone: true,
    imports: [
        BsDropdownModule
    ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private router: Router, private userService: UserService) { }

  name!: string;

  ngOnInit() {
    this.userService.$name.subscribe(name => {
      this.name = name;
    })
  }

  cart() {
    const token = window.localStorage.getItem("token");
    if (token) {
      window.location.href = "/cart";
    } else {
      Swal.fire({
        icon: "warning",
        title: "You have to login first",
        showConfirmButton: false,
        width: 400,
        heightAuto: true,
        timer: 1000,
        timerProgressBar: true,
      });
      this.router.navigate(["/login"]);
    }
  }
}
