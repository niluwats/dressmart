package com.nw.dressmart.controller;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequestDto;
import com.nw.dressmart.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryRequestDto request){
        return new ResponseEntity<CategoryDto>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> viewCategories(){
        return  ResponseEntity.ok(categoryService.viewCategories());
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> viewCategory(@PathVariable("categoryId") Long id){
        return ResponseEntity.ok(categoryService.viewCategory(id));
    }

    @PutMapping("{categoryId}")
    public String updateCategory(@PathVariable("categoryId") Long id,
                                 @RequestBody @Valid CategoryRequestDto categoryRequest){
        return categoryService.updateCategory(id,categoryRequest);
    }

    @DeleteMapping("{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Long id){
        return categoryService.deleteCategory(id);
    }
}
