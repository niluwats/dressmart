package com.nw.dressmart.service;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryRequest categoryRequest);

    List<CategoryDto> viewCategories();

    CategoryDto viewCategory(Long id);

    String updateCategory(Long id,CategoryRequest categoryRequest);

    String deleteCategory(Long id);
}
