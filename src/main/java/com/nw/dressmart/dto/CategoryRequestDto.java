package com.nw.dressmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = "field name cannot be empty")
    @NotNull(message = "field name is null")
    @Size(min = 2,message = "name should have at least 2 characters")
    private String name;
}
