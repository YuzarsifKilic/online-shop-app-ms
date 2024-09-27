package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
