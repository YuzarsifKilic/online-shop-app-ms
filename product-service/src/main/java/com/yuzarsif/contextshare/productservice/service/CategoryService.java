package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CategoryDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCategoryRequest;
import com.yuzarsif.contextshare.productservice.exception.AlreadyExistsException;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryDto::convert)
                .toList();
    }

    protected Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category didnt find by id: " + id));
    }

    public CategoryDto createCategory(CreateCategoryRequest request) {

        if (categoryRepository.findByName(request.name()).isPresent()) {
            throw new AlreadyExistsException("Category already exists with name: " + request.name());
        }

        Category category = Category
                .builder()
                .name(request.name())
                .build();

        return CategoryDto.convert(categoryRepository.save(category));
    }
}
