package com.nw.dressmart.service;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequest;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.entity.Item;
import com.nw.dressmart.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Optional<Category> optionalCategory=categoryRepository.findByName(categoryRequest.getName());
        if (optionalCategory.isPresent()) {
            throw new IllegalStateException("category name "+categoryRequest.getName()+" already exists");
        }

        Category category=modelMapper.map(categoryRequest,Category.class);
        category.setStatus(true);

        categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> viewCategories() {
        List<Category> categories=categoryRepository.findAll();
        return  categories.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto viewCategory(Long id) {
        Category category= categoryRepository.findById(id).orElseThrow(()->
                new IllegalStateException("category with ID "+id+" doesn't exists"));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Transactional
    @Override
    public String updateCategory(Long id,CategoryRequest categoryRequest) {
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

    private CategoryDto convertToDto(Category category){
        return modelMapper.map(category,CategoryDto.class);
    }
}
