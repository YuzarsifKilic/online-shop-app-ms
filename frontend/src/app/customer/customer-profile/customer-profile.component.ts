import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-customer-profile',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './customer-profile.component.html',
  styleUrl: './customer-profile.component.css'
})
export class CustomerProfileComponent {

  menuItems = [
    {name: "Siparişlerim", icon: "bi bi-box-seam"},
    {name: "Değerlendirmelerim", icon: "bi bi-chat-left"},
    {name: "Sorularım", icon: "bi bi-patch-question"},
    {name: "Kullanıcı Bilgilerim", icon: "bi bi-person"},
    {name: "Adres Bilgilerim", icon: "bi bi-house-add"},
  ]

}
