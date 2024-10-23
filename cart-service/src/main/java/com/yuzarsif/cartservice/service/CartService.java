package com.yuzarsif.cartservice.service;

import com.yuzarsif.cartservice.client.*;
import com.yuzarsif.cartservice.dto.CartDto;
import com.yuzarsif.cartservice.dto.CreateCartRequest;
import com.yuzarsif.cartservice.dto.ProductDto;
import com.yuzarsif.cartservice.exception.EntityNotFoundException;
import com.yuzarsif.cartservice.model.Cart;
import com.yuzarsif.cartservice.model.Product;
import com.yuzarsif.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final StockClient stockClient;

    public CartDto createCart(CreateCartRequest request) {
        Boolean checkUserExists = userClient.checkUserExists(request.customerId());

        if (!checkUserExists) {
            throw new EntityNotFoundException("Customer not found by id: " + request.customerId());
        }


        Boolean existsById = productClient.existById(request.productId());

        if (!existsById) {
            throw new EntityNotFoundException("Product not found by id: " + request.productId());
        }

        Optional<Cart> cart = cartRepository.findByCustomerId(request.customerId());

        if (cart.isPresent()) {
            for (Iterator<Product> iterator = cart.get().getProducts().iterator(); iterator.hasNext(); ) {
                Product product = iterator.next();
                if (product.getProductId().equals(request.productId())) {
                    if (product.getQuantity().equals(1) && request.quantity().equals(-1)) {
                        iterator.remove();
                        cartRepository.save(cart.get());
                        return getCartByCustomerId(request.customerId());
                    } else {
                        product.setQuantity(product.getQuantity() + request.quantity());
                    }
                    cartRepository.save(cart.get());
                    return getCartByCustomerId(request.customerId());
                }
            }
            Product product1 = Product
                    .builder()
                    .productId(request.productId())
                    .quantity(request.quantity())
                    .build();
            cart.get().getProducts().add(product1);
            cartRepository.save(cart.get());
            return getCartByCustomerId(request.customerId());
        }
        Product product = Product
                .builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
        Set<Product> products = new HashSet<>();
        products.add(product);
        Cart cart1 = Cart
                .builder()
                .customerId(request.customerId())
                .products(products)
                .build();
        cartRepository.save(cart1);
        return getCartByCustomerId(request.customerId());
    }

    public CartDto getCartByCustomerId(String customerId) {
        Optional<Cart> cartByCustomerId = cartRepository.findByCustomerId(customerId);

        List<Product> productsByCart = cartByCustomerId.get().getProducts().stream().toList();

        List<ProductDto> products = new ArrayList<>();

        List<Long> productIds = productsByCart.stream().map(product -> product.getProductId()).toList();

        List<ProductResponse> productListResponse = productClient.getProductsByIds(productIds);

        IntStream.range(0, productListResponse.size()).forEach(i -> {
            StockResponse stockByProductId = stockClient.findStockByProductId(productsByCart.get(i).getProductId());

            ProductDto product = new ProductDto(
                    productListResponse.get(i).id(),
                    productListResponse.get(i).name(),
                    productListResponse.get(i).price(),
                    productListResponse.get(i).mainImageUrl(),
                    productsByCart.get(i).getQuantity(),
                    stockByProductId.quantity());

            products.add(product);
        });

        return new CartDto(customerId, products);
    }
}
