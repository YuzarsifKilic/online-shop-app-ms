package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreatePhotoRequest;
import com.yuzarsif.contextshare.productservice.dto.PhotoDto;
import com.yuzarsif.contextshare.productservice.model.Photo;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ProductService productService;

    public void createPhoto(CreatePhotoRequest request) {
        Product product = productService.getProduct(request.productId());

        IntStream.range(0, request.urls().size()).forEach(i -> {
            Photo photo = Photo
                    .builder()
                    .url(request.urls().get(i))
                    .order(i)
                    .product(product)
                    .build();
            photoRepository.save(photo);
        });
    }

    public List<PhotoDto> getPhotosByProductId(Long productId) {
        return photoRepository
                .findAllByProductId(productId)
                .stream()
                .map(PhotoDto::convert)
                .toList();
    }
}
