package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CategoryDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCategoryRequest;
import com.yuzarsif.contextshare.productservice.exception.AlreadyExistsException;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.repository.CampaignRepository;
import com.yuzarsif.contextshare.productservice.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory_WhenRequestIsValid_ShouldReturnCategoryDto() {

        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory());

        CategoryDto response = categoryService.createCategory(mockCreateCategoryRequest());

        assertNotNull(response);
        assertEquals("category_name", response.name());
        assertEquals(1, response.id());
    }

    @Test
    public void testCreateCategory_WhenNameAlreadyExists_ShouldThrowAlreadyExistsException() {

        when(categoryRepository.findByName(mockCreateCategoryRequest().name())).thenReturn(Optional.ofNullable(mockCategory()));

        AlreadyExistsException alreadyExistsException = assertThrows(AlreadyExistsException.class, () -> categoryService.createCategory(mockCreateCategoryRequest()));

        assertEquals("Category already exists with name: category_name", alreadyExistsException.getMessage());
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    public void testGetCategories_ShouldReturnCategoryDtoList() {

        when(categoryRepository.findAll()).thenReturn(List.of(mockCategory()));

        List<CategoryDto> response = categoryService.getCategories();

        assertEquals(List.of(CategoryDto.convert(mockCategory())), response);
        verify(categoryRepository, times(1)).findAll();
    }

    private CreateCategoryRequest mockCreateCategoryRequest() {
        return new CreateCategoryRequest("category_name");
    }

    private Category mockCategory() {
        return Category
                .builder()
                .id(1)
                .name("category_name")
                .build();
    }


}
