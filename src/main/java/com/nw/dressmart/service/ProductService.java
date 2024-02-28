package com.nw.dressmart.service;

import com.nw.dressmart.dto.ProductDto;
import com.nw.dressmart.dto.ProductRequestDto;

import java.util.List;

public interface ProductService{
    ProductDto createProduct(ProductRequestDto productRequest);

    List<ProductDto> viewProducts();

    ProductDto viewProduct(Long id);

    String updateProduct(Long id, ProductRequestDto productRequest);

    String deleteProduct(Long id);
}
