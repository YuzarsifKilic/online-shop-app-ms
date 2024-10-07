package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreatePhotoRequest;
import com.yuzarsif.contextshare.productservice.dto.PhotoDto;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Photo;
import com.yuzarsif.contextshare.productservice.repository.CategoryRepository;
import com.yuzarsif.contextshare.productservice.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PhotoServiceTest {

    @InjectMocks
    private PhotoService photoService;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePhoto_WhenProductExists_ShouldReturnPhotoDtoList() {

        when(photoRepository.save(any())).thenReturn(mockPhoto());

        List<PhotoDto> photos = photoService.createPhoto(mockCreatePhotoRequest());

        assertNotNull(photos);
        assertEquals(1, photos.size());
        assertEquals(1, photos.get(0).id());
        assertEquals("photo_url", photos.get(0).url());
        assertEquals(1, photos.get(0).order());
    }

    @Test
    public void testCreatePhoto_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(productService.getProduct(mockCreatePhotoRequest().productId()))
                .thenThrow(new EntityNotFoundException("Product not found with id: 1"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> photoService.createPhoto(mockCreatePhotoRequest()));

        assertEquals("Product not found with id: 1", exception.getMessage());
    }

    @Test
    public void testGetPhotosByProductId_WhenProductExists_ShouldReturnPhotoDtoList() {

        when(photoRepository.findAllByProductId(1L)).thenReturn(List.of(mockPhoto()));

        List<PhotoDto> photos = photoService.getPhotosByProductId(1L);

        assertNotNull(photos);
        assertEquals(1, photos.size());
        assertEquals(1, photos.get(0).id());
        assertEquals("photo_url", photos.get(0).url());
        assertEquals(1, photos.get(0).order());
    }

    @Test
    public void testGetPhotosByProductId_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(productService.getProduct(1L))
                .thenThrow(new EntityNotFoundException("Product not found with id: 1"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> photoService.getPhotosByProductId(1L));

        assertEquals("Product not found with id: 1", exception.getMessage());
    }

    private CreatePhotoRequest mockCreatePhotoRequest() {
        return new CreatePhotoRequest(1L, List.of("url1"));
    }

    private PhotoDto mockPhotoDto() {
        return new PhotoDto(1L, "photo_url", 1);
    }

    private Photo mockPhoto() {
        return Photo
                .builder()
                .id(1L)
                .url("photo_url")
                .order(1)
                .build();
    }
}
