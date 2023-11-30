package com.nw.dressmart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {
    @NotNull(message = "item ID cannot be null")
    @Min(value = 1,message = "category ID cannot be 0 or negative")
    private Long itemId;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1,message = "quantity cannot be 0 or negative")
    private Integer quantity;
}
