package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdOn;
    private String imageUrl;
    private Boolean status;
}
