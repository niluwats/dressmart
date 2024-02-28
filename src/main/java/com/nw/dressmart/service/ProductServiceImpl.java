package com.nw.dressmart.service;

import com.nw.dressmart.dto.ProductDto;
import com.nw.dressmart.dto.ProductRequestDto;
import com.nw.dressmart.entity.Category;
import com.nw.dressmart.entity.Product;
import com.nw.dressmart.mappers.CustomProductMapper;
import com.nw.dressmart.repository.CategoryRepository;
import com.nw.dressmart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements  ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomProductMapper productMapper;

    @Override
    public ProductDto createProduct(ProductRequestDto productRequest) {
        Optional<Product> optionalProduct=productRepository.findByName(productRequest.getName());
        if (optionalProduct.isPresent()) {
            throw new IllegalStateException("product name "+productRequest.getName()+" already exists");
        }

        Category category=categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->
                new IllegalStateException("category with ID "+productRequest.getCategoryId()+" doesn't exists"));

        Product product=productMapper.productDtoToProduct(productRequest);
        product.setCategory(category);
        productRepository.save(product);

        return productMapper.productToProductDto(product);
    }

    @Override
    public List<ProductDto> viewProducts() {
        List<Product> products=productRepository.findAll();
        return  products.stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto viewProduct(Long id) {
       Product product= productRepository.findById(id).orElseThrow(()->
                new IllegalStateException("product with ID "+id+" doesn't exists"));

        return productMapper.productToProductDto(product);
    }

    @Transactional
    @Override
    public String updateProduct(Long id, ProductRequestDto productRequest) {
        Product product=productRepository.findById(id).orElseThrow(()->
                new IllegalStateException("product with ID "+id+" doesn't exists"));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        if(!Objects.equals(productRequest.getCategoryId(),product.getCategory().getId())){
            Category category=categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(()->
                    new IllegalStateException("category with ID "+productRequest.getCategoryId()+" doesn't exists"));
            product.setCategory(category);
        }

        return "updated";
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(()->
                new IllegalStateException("product with ID "+id+" doesn't exists"));

        productRepository.deleteById(id);
        return "deleted";
    }
}
