import {Component, EventEmitter, Output} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {CategoryService} from "../_services/category.service";
import {Category} from "../_models/category";
import {NgForOf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Filter} from "../_models/filter";

@Component({
  selector: 'app-filter',
  standalone: true,
  imports: [HttpClientModule, NgForOf, ReactiveFormsModule],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css'
})
export class FilterComponent {

  @Output() filter: EventEmitter<Filter> = new EventEmitter<Filter>();

  constructor(private categoryService: CategoryService, private formBuilder: FormBuilder) {
    this.formGroup = this.formBuilder.group({
      name: undefined,
      min: undefined,
      max: undefined,
      categoryId: ''
    });
  }

  categories: Category[] = [];
  formGroup: FormGroup;

  ngOnInit() {
    this.categoryService.getCategories().subscribe({
      next: (response) => {
        this.categories = response;
      },
      error: (error) => {
        console.error('Error fetching data', error);
      }
    });
  }

  filterProducts() {
    console.log(this.formGroup.value);
    let filter: Filter;
    filter = {
      name: this.formGroup.value.name,
      min: this.formGroup.value.min,
      max: this.formGroup.value.max,
      categoryId: this.formGroup.value.categoryId
    }
    this.filter.emit(filter);
  }
}
