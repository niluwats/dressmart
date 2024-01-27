package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.CategoryDto;
import com.nw.dressmart.dto.CategoryRequestDto;
import com.nw.dressmart.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category categoryDtoToCategory(CategoryRequestDto categoryRequestDto);

    CategoryDto categoryToCategoryDto(Category category);
}
