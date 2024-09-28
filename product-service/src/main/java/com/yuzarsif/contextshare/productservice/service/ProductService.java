package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto saveProduct(CreateProductRequest request) {
        Product product = Product
                .builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .mainImageUrl(request.mainImageUrl())
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
}
