package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.client.CompanyResponse;
import com.yuzarsif.contextshare.productservice.client.UserClient;
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
    private final UserClient userClient;

    public ProductDto saveProduct(CreateProductRequest request) {
        Boolean existCompany = userClient.existCompanyById(request.companyId());

        if (!existCompany) {
            throw new EntityNotFoundException("Company not found with id: " + request.companyId());
        }

        CompanyResponse company = userClient.getCompanyById(request.companyId());

        Category category = categoryService.findById(request.categoryId());

        Product product = Product
                .builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .mainImageUrl(request.mainImageUrl())
                .companyId(request.companyId())
                .category(category)
                .build();

        return ProductDto.convert(productRepository.save(product), company);
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
        Product product = getProduct(id);

        CompanyResponse company = userClient.getCompanyById(product.getCompanyId());

        return ProductDto.convert(product, company);
    }

    public Boolean existById(Long id) {
        return productRepository.existsById(id);
    }

    public List<ProductList> getFilteredProducts(ProductSearchCriteria criteria) {
        Specification<Product> specification = new ProductSpesification(criteria);
        return productRepository.findAllProductList(specification);
    }

    public List<ProductList> getProductListById(List<Long> id) {
        return productRepository.findByIdIn(id);
    }
}
