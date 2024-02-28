package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.ProductDto;
import com.nw.dressmart.dto.ProductRequestDto;
import com.nw.dressmart.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class CustomProductMapper implements ProductMapper {
    @Override
    public ProductDto productToProductDto(Product product) {
        ProductDto productDto =new ProductDto();
       productDto.setId(product.getId());
       productDto.setName(product.getName());
       productDto.setDescription(product.getDescription());
       productDto.setPrice(product.getPrice());
       productDto.setCategoryName(product.getCategory().getName());
       productDto.setStatus(product.getStatus());
       return productDto;
    }

    @Override
    public Product productDtoToProduct(ProductRequestDto productRequestDto) {
        Product product=new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStatus(true);
        return product;
    }
}
