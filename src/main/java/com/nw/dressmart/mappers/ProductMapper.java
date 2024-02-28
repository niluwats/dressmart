package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.ProductDto;
import com.nw.dressmart.dto.ProductRequestDto;
import com.nw.dressmart.entity.Product;

public interface ProductMapper {
    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductRequestDto productRequestDto);
}
