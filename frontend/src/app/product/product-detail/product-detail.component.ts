import {Component, ElementRef, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../_services/product.service";
import {Product} from "../../_models/product";
import {NgForOf} from "@angular/common";
import {PhotoService} from "../../_services/photo.service";
import {Photo} from "../../_models/photo";
import {BsDropdownModule} from "ngx-bootstrap/dropdown";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {ProductDescriptionComponent} from "./product-description/product-description.component";

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [
    NgForOf,
    BsDropdownModule,
    MatTabGroup,
    MatTab,
    ProductDescriptionComponent
  ],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent {

  @ViewChild('innerContainer', { read: ElementRef }) innerContainer!: ElementRef;

  constructor(private route: ActivatedRoute, private productService: ProductService, private photoService: PhotoService) { }

  productId!: number;
  product!: Product;
  mainImageUrl!: string;
  photos: Photo[] = [];
  visiblePhotos: Photo[] = [];
  currentIndex = 0; // Şu anda gösterilen başlangıç indeksi
  maxVisiblePhotos = 4; // Her seferde göstermek istediğimiz fotoğraf sayısı

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.productId = params['productId'];
      this.productService.getProductById(params['productId'])
        .subscribe({
          next: (response) => {
            this.product = response;
            this.mainImageUrl = this.product.mainImageUrl;
          },
          error: (error) => {
            console.error('Error fetching product data', error);
          }
        });

      this.photoService.getPhotos(params['productId'])
        .subscribe({
          next: (response) => {
            this.photos = response;
            this.updateVisiblePhotos(); // İlk yüklemede görünür resimleri güncelle
          },
          error: (error) => {
            console.error('Error fetching photo data', error);
          }
        });
    });
  }

  updateVisiblePhotos() {
    this.visiblePhotos = this.photos.slice(this.currentIndex, this.currentIndex + this.maxVisiblePhotos);
  }

  scrollRight() {
    // Sağ ok tıklanırsa ve daha fazla resim varsa, 4 resim kaydır
    if (this.currentIndex + this.maxVisiblePhotos < this.photos.length) {
      this.currentIndex += this.maxVisiblePhotos;
      this.updateVisiblePhotos();
    }
  }

  scrollLeft() {
    // Sol ok tıklanırsa ve daha önceki resimler varsa, 4 resim sola kaydır
    if (this.currentIndex - this.maxVisiblePhotos >= 0) {
      this.currentIndex -= this.maxVisiblePhotos;
      this.updateVisiblePhotos();
    }
  }

  changeImage(url: string) {
    this.mainImageUrl = url;
  }
}
