package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreatePhotoRequest;
import com.yuzarsif.contextshare.productservice.dto.PhotoDto;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Photo;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final ProductService productService;

    public List<PhotoDto> createPhoto(CreatePhotoRequest request) {
        Product product = productService.getProduct(request.productId());

        List<PhotoDto> photos = new ArrayList<>();

        IntStream.range(0, request.urls().size()).forEach(i -> {
            Photo photo = Photo
                    .builder()
                    .url(request.urls().get(i))
                    .order(i)
                    .product(product)
                    .build();
            Photo savedPhoto = photoRepository.save(photo);

            photos.add(PhotoDto.convert(savedPhoto));
        });

        return photos;
    }

    public List<PhotoDto> getPhotosByProductId(Long productId) {
        List<PhotoDto> photoList = photoRepository
                .findAllByProduct_IdOrderByOrderAsc(productId)
                .stream()
                .map(PhotoDto::convert)
                .toList();

        if (photoList.isEmpty()) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }

        return photoList;
    }
}
