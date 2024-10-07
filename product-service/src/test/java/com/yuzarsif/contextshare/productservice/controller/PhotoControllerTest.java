package com.yuzarsif.contextshare.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.contextshare.productservice.dto.CreatePhotoRequest;
import com.yuzarsif.contextshare.productservice.dto.PhotoDto;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.service.CategoryService;
import com.yuzarsif.contextshare.productservice.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PhotoService photoService;

    @Test
    public void testCreatePhoto_WhenRequestIsValid_ShouldReturnPhotoDtoList() throws Exception {

        when(photoService.createPhoto(mockCreatePhotoRequest())).thenReturn(List.of(mockPhotoDto()));

        mockMvc.perform(post("/api/v1/photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreatePhotoRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].url").value("photo_url"))
                .andExpect(jsonPath("$[0].order").value(1));
    }

    @Test
    public void testCreatePhoto_WhenRequestIsInvalid_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreatePhotoRequestInvalid())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testCreatePhoto_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(photoService.createPhoto(mockCreatePhotoRequest())).thenThrow(new EntityNotFoundException("Product not found with id: 1"));

        mockMvc.perform(post("/api/v1/photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreatePhotoRequest())))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testGetPhotosByProductId_ShouldReturnPhotoDtoList() throws Exception {

        when(photoService.getPhotosByProductId(1L)).thenReturn(List.of(mockPhotoDto()));

        mockMvc.perform(get("/api/v1/photos/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].url").value("photo_url"))
                .andExpect(jsonPath("$[0].order").value(1));
    }

    private CreatePhotoRequest mockCreatePhotoRequest() {
        return new CreatePhotoRequest(1L, List.of("url1"));
    }

    private CreatePhotoRequest mockCreatePhotoRequestInvalid() {
        return new CreatePhotoRequest(1L, List.of());
    }

    private PhotoDto mockPhotoDto() {
        return new PhotoDto(1L, "photo_url", 1);
    }
}
