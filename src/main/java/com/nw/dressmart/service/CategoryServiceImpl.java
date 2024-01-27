package com.nw.dressmart.service;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequestDto;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.mappers.CategoryMapper;
import com.nw.dressmart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryRequestDto categoryRequest) {
        Optional<Category> optionalCategory=categoryRepository.findByName(categoryRequest.getName());
        if (optionalCategory.isPresent()) {
            throw new IllegalStateException("category name "+categoryRequest.getName()+" already exists");
        }

        Category category=categoryMapper.categoryDtoToCategory(categoryRequest);
        category.setStatus(true);

        categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> viewCategories() {
        List<Category> categories=categoryRepository.findAll();
        return  categories.stream().map(categoryMapper::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto viewCategory(Long id) {
        Category category= categoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("category with ID "+id+" doesn't exists"));

        return categoryMapper.categoryToCategoryDto(category);
    }

    @Transactional
    @Override
    public String updateCategory(Long id, CategoryRequestDto categoryRequest) {
        Category category=categoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("category with ID "+id+" doesn't exists"));

        category.setName(categoryRequest.getName());
        return "updated";
    }

    @Override
    public String deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("category with ID "+id+" doesn't exists"));

        categoryRepository.deleteById(id);
        return "deleted";
    }
}
