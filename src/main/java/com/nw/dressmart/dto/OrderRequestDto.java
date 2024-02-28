package com.nw.dressmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "order items cannot be null")
    @Size(min = 1,message = "order items cannot be empty")
    private Map<String,@NotNull Integer> orderItems;
}
