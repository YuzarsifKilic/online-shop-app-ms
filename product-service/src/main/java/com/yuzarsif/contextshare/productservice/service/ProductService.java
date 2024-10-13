package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.dto.ProductList;
import com.yuzarsif.contextshare.productservice.dto.ProductSearchCriteria;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.ProductRepository;
import com.yuzarsif.contextshare.productservice.spesification.ProductSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductDto saveProduct(CreateProductRequest request) {
        Category category = categoryService.findById(request.categoryId());

        Product product = Product
                .builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .mainImageUrl(request.mainImageUrl())
                .category(category)
                .build();

        return ProductDto.convert(productRepository.save(product));
    }

    public Product getProduct(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    protected List<Product> getProductsById(List<Long> idList) {
        return productRepository
                .findAllById(idList);
    }

    public ProductDto findProductById(Long id) {
        return ProductDto.convert(getProduct(id));
    }

    public Boolean existById(Long id) {
        return productRepository.existsById(id);
    }

    public List<ProductList> getFilteredProducts(String name, Double min, Double max, Integer categoryId) {
        Specification<Product> specification = new ProductSpesification(new ProductSearchCriteria(name, min, max, categoryId));
        return productRepository.findAllProductList(specification);
    }
}
