package com.nw.dressmart.service;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequestDto categoryRequest);

    List<CategoryDto> viewCategories();

    CategoryDto viewCategory(Long id);

    String updateCategory(Long id, CategoryRequestDto categoryRequest);

    String deleteCategory(Long id);
}
