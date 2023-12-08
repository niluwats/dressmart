package com.nw.dressmart.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ItemRequestDto {
    @NotBlank(message = "name cannot be empty")
    @NotNull(message = "name is null")
    @Size(min = 2,message = "name should have at least 2 characters")
    private String name;

    @NotBlank(message = "description cannot be empty")
    @NotNull(message = "description is null")
    @Size(min = 2,message = "description should have at least 2 characters")
    private String description;

    @NotNull(message = "price cannot be null")
    @Min(value = 1,message = "price cannot be 0 or negative")
    private Double price;

    @NotNull(message = "category ID cannot be null")
    @Min(value = 1,message = "category ID cannot be 0 or negative")
    private Long categoryId;
}
