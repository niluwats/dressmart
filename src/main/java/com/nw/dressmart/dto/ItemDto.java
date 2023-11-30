package com.nw.dressmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private Integer quantity;
    private LocalDateTime createdOn;
    private String imageUrl;
    private Boolean status;
}
