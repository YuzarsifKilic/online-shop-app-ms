package com.yuzarsif.contextshare.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.contextshare.productservice.dto.CategoryDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCategoryRequest;
import com.yuzarsif.contextshare.productservice.exception.AlreadyExistsException;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.service.CampaignService;
import com.yuzarsif.contextshare.productservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testCreateCategory_WhenRequestValid_ShouldReturnCategoryDto() throws Exception {

        when(categoryService.createCategory(mockCreateCategoryRequest())).thenReturn(mockCategory());


        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateCategoryRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("category_name"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testCreateCategory_WhenCategoryNameAlreadyExists_ShouldThrowAlreadyExistsException() throws Exception {

        when(categoryService.createCategory(mockCreateCategoryRequest())).thenThrow(new AlreadyExistsException("Category already exists with name: category_name"));


        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateCategoryRequest())))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("ALREADY_EXISTS_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testCreateCategory_WhenRequestIsInvalid_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockInvalidCreateCategoryRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testGetAllCategories_ShouldReturnCategoryDtoList() throws Exception {

        when(categoryService.getCategories()).thenReturn(List.of(mockCategory()));


        mockMvc.perform(get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("category_name"))
                .andExpect(jsonPath("$[0].id").value(1));
    }


    private CreateCategoryRequest mockInvalidCreateCategoryRequest() {
        return new CreateCategoryRequest(null);
    }

    private CreateCategoryRequest mockCreateCategoryRequest() {
        return new CreateCategoryRequest("category_name");
    }

    private CategoryDto mockCategory() {
        return new CategoryDto(1, "category_name");
    }

}
