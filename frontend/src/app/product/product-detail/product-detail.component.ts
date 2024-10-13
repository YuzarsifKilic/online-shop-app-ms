import {Component, ElementRef, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../_services/product.service";
import {Product} from "../../_models/product";
import {NgForOf} from "@angular/common";
import {PhotoService} from "../../_services/photo.service";
import {Photo} from "../../_models/photo";

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
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
    this.visiblePhotos = this.photos.slice(this.currentIndex, this.currentIndex + 4); // 4 resim göster
  }

  scrollRight() {
    if (this.currentIndex + 4 < this.photos.length) {
      this.currentIndex += 1; // 1 sağa kaydır
      this.updateVisiblePhotos();
      this.updateScrollPosition();
    }
  }

  scrollLeft() {
    if (this.currentIndex > 0) {
      this.currentIndex -= 1; // 1 sola kaydır
      this.updateVisiblePhotos();
      this.updateScrollPosition();
    }
  }

  updateScrollPosition() {
    const scrollElement = this.innerContainer.nativeElement;
    const offset = this.currentIndex * 100; // Her resim 100px genişliğinde
    scrollElement.style.transform = `translateX(-${offset}px)`;
  }

  changeImage(url: string) {
    this.mainImageUrl = url;
  }
}
